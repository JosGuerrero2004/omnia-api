package org.josiasguerrero.products.application.usecase.Product;

import org.josiasguerrero.products.application.dto.request.UpdateProductRequest;
import org.josiasguerrero.products.application.dto.response.ProductResponse;
import org.josiasguerrero.products.application.mapper.ProductApplicationMapper;
import org.josiasguerrero.products.domain.entity.Product;
import org.josiasguerrero.products.domain.exception.DuplicateSkuException;
import org.josiasguerrero.products.domain.exception.ProductNotFoundException;
import org.josiasguerrero.products.domain.port.BrandRepository;
import org.josiasguerrero.products.domain.port.ProductRepository;
import org.josiasguerrero.products.domain.valueobject.BrandId;
import org.josiasguerrero.products.domain.valueobject.ProductId;
import org.josiasguerrero.products.domain.valueobject.Sku;
import org.josiasguerrero.shared.aplication.validation.DtoValidator;
import org.josiasguerrero.shared.domain.valueobject.Money;
import org.josiasguerrero.products.domain.valueobject.Barcode;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateProductUseCase {

  private final ProductRepository productRepository;
  private final BrandRepository brandRepository;
  private final DtoValidator dtoValidator;

  public ProductResponse execute(String productId, UpdateProductRequest request) {

    dtoValidator.validate(request);

    ProductId id = ProductId.from(productId);
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));

    validateBusinessRules(product, request);
    updateProductFields(product, request);

    productRepository.save(product);

    return ProductApplicationMapper.toResponse(product);
  }

  private void validateBusinessRules(Product product, UpdateProductRequest request) {
    if (request.sku() != null && !request.sku().isBlank()) {
      Sku newSku = Sku.from(request.sku());

      if (!product.getSku().equals(newSku)) {
        if (productRepository.existsBySku(newSku)) {
          throw new DuplicateSkuException(newSku);
        }
      }
    }

    // Validar que la marca exista (si viene)
    if (request.brandId() != null && !request.brandId().isBlank()) {
      BrandId brandId = BrandId.from(request.brandId());
      brandRepository.findById(brandId)
          .orElseThrow(() -> new IllegalArgumentException("Brand not found: " + brandId));
    }

    // Validar que price > cost (si vienen ambos o uno de los dos)
    if (request.cost() != null || request.price() != null) {
      Money newCost = request.cost() != null
          ? new Money(request.cost())
          : product.getCost();

      Money newPrice = request.price() != null
          ? new Money(request.price())
          : product.getPrice();

      if (!newPrice.isGreaterThan(newCost)) {
        throw new IllegalArgumentException("Price must be greater than cost");
      }
    }
  }

  private void updateProductFields(Product product, UpdateProductRequest request) {

    if (request.sku() != null && !request.sku().isBlank()) {
      product.changeSku(Sku.from(request.sku()));
    }

    if (request.name() != null && !request.name().isBlank()) {
      product.rename(request.name());
    }

    if (request.description() != null) {
      product.changeDescription(request.description());
    }

    if (request.barcode() != null && !request.barcode().isBlank()) {
      product.setBarcode(new Barcode(request.barcode()));
    }

    if (request.cost() != null || request.price() != null) {
      Money newCost = request.cost() != null ? new Money(request.cost()) : product.getCost();
      Money newPrice = request.price() != null ? new Money(request.price()) : product.getPrice();
      product.updatePricing(newCost, newPrice);
    }

    if (request.stock() != null) {
      product.adjustStock(request.stock());
    }

    if (request.brandId() != null && !request.brandId().isBlank()) {
      product.assignToBrand(BrandId.from(request.brandId()));
    }
  }

}
