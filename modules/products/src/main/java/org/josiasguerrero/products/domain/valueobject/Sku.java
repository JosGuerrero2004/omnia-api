package org.josiasguerrero.products.domain.valueobject;

public record Sku(String value) {
  public Sku {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("SKU cannot be null or empty");
    }

    value = value.trim().toUpperCase();
    if (value.length() > 50) {
      throw new IllegalArgumentException("SKU cannot exceed 50 characteres");
    }
  }

  public static Sku from(String value) {
    return new Sku(value);

  }
}
