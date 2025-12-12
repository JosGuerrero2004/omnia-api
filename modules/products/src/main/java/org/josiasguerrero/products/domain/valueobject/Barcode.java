package org.josiasguerrero.products.domain.valueobject;

import java.util.regex.Pattern;

public record Barcode(String value) {
  // Patrón para códigos de barras comunes (solo dígitos)
  private static final Pattern BARCODE_PATTERN = Pattern.compile("^[0-9]{8,14}$");

  public Barcode {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("Barcode cannot be null or empty");
    }

    value = value.trim();

    if (value.length() > 50) {
      throw new IllegalArgumentException(
          "Barcode cannot exceed 50 characters: " + value);
    }

    // Validación básica: solo números para códigos estándar
    if (!BARCODE_PATTERN.matcher(value).matches()) {
      throw new IllegalArgumentException(
          "Barcode must contain only digits (8-14 characters): " + value);
    }
  }

  public static Barcode of(String value) {
    return new Barcode(value);
  }
}
