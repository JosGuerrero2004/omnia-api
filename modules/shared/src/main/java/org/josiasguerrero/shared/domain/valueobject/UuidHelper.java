// shared/domain/valueobject/UuidHelper.java
package org.josiasguerrero.shared.domain.valueobject;

import java.util.UUID;

public final class UuidHelper {

  private UuidHelper() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static UUID generate() {
    return UUID.randomUUID();
  }

  public static UUID parse(String raw) {
    try {
      return UUID.fromString(raw);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid UUID format: " + raw, e);
    }
  }
}
