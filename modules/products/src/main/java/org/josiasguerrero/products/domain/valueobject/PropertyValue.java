package org.josiasguerrero.products.domain.valueobject;

public record PropertyValue(String value) {
  public PropertyValue {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("Property value cannot be null or empty");
    }

    value = value.trim();

    if (value.length() > 100) {
      throw new IllegalArgumentException(
          "Property value cannot exceed 100 characters: " + value);
    }
  }

  public static PropertyValue of(String value) {
    return new PropertyValue(value);
  }

  public boolean isEmpty() {
    return value.isBlank();
  }

  public boolean equalsIgnoreCase(String other) {
    return value.equalsIgnoreCase(other);
  }

  @Override
  public String toString() {
    return value;
  }
}
