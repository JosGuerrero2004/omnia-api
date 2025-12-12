package org.josiasguerrero.products.domain.valueobject;

import java.util.UUID;

import org.josiasguerrero.shared.domain.valueobject.EntityId;
import org.josiasguerrero.shared.domain.valueobject.UuidHelper;

public record ProductId(UUID value) implements EntityId {
  public ProductId {
    if (value == null) {
      throw new IllegalArgumentException("ProductId cannot be null");
    }
  }

  public static ProductId generate() {
    return new ProductId(UuidHelper.generate());
  }

  public static ProductId from(String raw) {
    return new ProductId(UuidHelper.parse(raw));
  }
}
