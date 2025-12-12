package org.josiasguerrero.products.domain.valueobject;

import org.josiasguerrero.shared.domain.valueobject.EntityId;

public record PropertyId(Integer value) implements EntityId {
  public PropertyId {
    if (value == null || value <= 0) {
      throw new IllegalArgumentException("PropertyId must be a positive integer");
    }
  }

  public static PropertyId from(Integer id) {
    return new PropertyId(id);
  }

  public static PropertyId from(String raw) {
    try {
      return new PropertyId(Integer.parseInt(raw));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid PropertyId format: " + raw, e);
    }
  }
}
