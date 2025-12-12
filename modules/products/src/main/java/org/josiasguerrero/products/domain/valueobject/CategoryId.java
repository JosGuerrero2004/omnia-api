package org.josiasguerrero.products.domain.valueobject;

import org.josiasguerrero.shared.domain.valueobject.EntityId;

public record CategoryId(Integer value) implements EntityId {
  public CategoryId {
    if (value == null || value <= 0) {
      throw new IllegalArgumentException("CategoryId must be a positive integer");
    }
  }

  public static CategoryId from(Integer id) {
    return new CategoryId(id);
  }

  public static CategoryId from(String raw) {
    try {
      return new CategoryId(Integer.parseInt(raw));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid CategoryId format: " + raw, e);
    }
  }
}
