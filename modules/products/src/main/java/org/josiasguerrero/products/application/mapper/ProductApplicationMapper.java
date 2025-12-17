package org.josiasguerrero.products.application.mapper;

import java.util.stream.Collectors;

import org.josiasguerrero.products.application.dto.response.ProductResponse;
import org.josiasguerrero.products.domain.entity.Product;

public class ProductApplicationMapper {

  public static ProductResponse toResponse(Product product) {

    return new ProductResponse(
        product.getId().value().toString(),
        product.getSku().value(),
        product.getName(),
        product.getDescription(),
        product.getBarcode() != null ? product.getBarcode().value() : null,
        product.getCost().amount(),
        product.getPrice().amount(),
        product.getStock().quantity(),
        product.getBrandId() != null ? product.getBrandId().value().toString() : null,
        product.getCategoryIds().stream()
            .map(catId -> catId.value())
            .collect(Collectors.toSet()),
        product.getProperties().entrySet().stream()
            .collect(Collectors.toMap(
                entry -> entry.getKey().value(),
                entry -> entry.getValue().value())),
        product.getCreatedAt(),
        product.getUpdatedAt());
  }

}
