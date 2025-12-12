package org.josiasguerrero.products.domain.valueobject;

import org.josiasguerrero.shared.domain.valueobject.EntityId;

public record BrandId(Integer value) implements EntityId {
  public BrandId {
    if (value == null || value <= 0) {
      throw new IllegalArgumentException("BrandId must be a positive integer");
    }
  }

  public static BrandId from(Integer id) {
    return new BrandId(id);
  }

  public static BrandId from(String raw) {
    try {
      return new BrandId(Integer.parseInt(raw));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid BrandId format: " + raw, e);
    }
  }

}
