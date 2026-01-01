package org.josiasguerrero.products.application.usecase.Product;

import java.util.List;

import org.josiasguerrero.products.application.dto.response.ProductResponse;
import org.josiasguerrero.products.application.mapper.ProductApplicationMapper;
import org.josiasguerrero.products.domain.port.ProductRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindAllProductsUseCase {
  private ProductRepository productRepository;
  private final ProductApplicationMapper productApplicationMapper;

  public List<ProductResponse> execute() {
    return productRepository.findAll().stream().map(productApplicationMapper::toResponse).toList();
  }
}
