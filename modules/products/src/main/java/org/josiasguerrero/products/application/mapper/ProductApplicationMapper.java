package org.josiasguerrero.products.application.mapper;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.josiasguerrero.products.application.dto.response.BrandPResponse;
import org.josiasguerrero.products.application.dto.response.CategoryPResponse;
import org.josiasguerrero.products.application.dto.response.ProductResponse;
import org.josiasguerrero.products.domain.entity.Product;
import org.josiasguerrero.products.domain.port.BrandRepository;
import org.josiasguerrero.products.domain.port.CategoryRepository;
import org.josiasguerrero.products.domain.port.PropertyRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductApplicationMapper {

  private final BrandRepository brandRepository;
  private final CategoryRepository categoryRepository;
  private final PropertyRepository propertyRepository;

  public ProductResponse toResponse(Product product) {
    // Marca
    BrandPResponse brandDto = Optional.ofNullable(product.getBrandId())
        .flatMap(id -> brandRepository.findById(id)
            .map(b -> new BrandPResponse(b.getId().value(), b.getName())))
        .orElse(null);

    // Categorías
    Set<CategoryPResponse> categories = product.getCategoryIds().stream()
        .map(catId -> categoryRepository.findById(catId)
            .map(cat -> new CategoryPResponse(cat.getId().value(), cat.getName())))
        .flatMap(Optional::stream)
        .collect(Collectors.toSet());

    // Propiedades
    Map<String, String> properties = product.getProperties().entrySet().stream()
        .map(entry -> propertyRepository.findById(entry.getKey())
            .map(prop -> Map.entry(prop.getName(), entry.getValue().value())))
        .flatMap(Optional::stream)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    // Barcode seguro
    String barcode = product.getBarcode() != null ? product.getBarcode().value() : null;

    // Construcción del DTO final
    return new ProductResponse(
        product.getId().value().toString(),
        product.getSku().value(),
        product.getName(),
        product.getDescription(),
        barcode,
        product.getCost().amount(),
        product.getPrice().amount(),
        product.getStock().quantity(),
        brandDto,
        categories,
        properties,
        product.getCreatedAt(),
        product.getUpdatedAt());
  }
}
