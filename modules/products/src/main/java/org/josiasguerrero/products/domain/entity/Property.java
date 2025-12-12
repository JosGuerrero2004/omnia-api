package org.josiasguerrero.products.domain.entity;

import java.time.LocalDateTime;

import org.josiasguerrero.products.domain.valueobject.PropertyId;

public class Property {

  private PropertyId id;
  private String name;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Property(PropertyId id, String name) {
    this.id = id;
    this.name = validateName(name);
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  public Property(
      PropertyId id,
      String name,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.name = validateName(name);
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public void updateName(String newName) {
    this.name = validateName(newName);
    this.updatedAt = LocalDateTime.now();
  }

  private String validateName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Property name cannot be null or empty");
    }
    String trimmed = name.trim();
    if (trimmed.length() > 50) {
      throw new IllegalArgumentException("Property name cannot exceed 50 characters");
    }
    return trimmed;
  }

  public PropertyId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
