package org.josiasguerrero.products.domain.entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.josiasguerrero.products.domain.valueobject.Barcode;
import org.josiasguerrero.products.domain.valueobject.BrandId;
import org.josiasguerrero.products.domain.valueobject.CategoryId;
import org.josiasguerrero.products.domain.valueobject.ProductId;
import org.josiasguerrero.products.domain.valueobject.PropertyId;
import org.josiasguerrero.products.domain.valueobject.PropertyValue;
import org.josiasguerrero.products.domain.valueobject.Sku;
import org.josiasguerrero.products.domain.valueobject.Stock;
import org.josiasguerrero.shared.domain.valueobject.Money;

public class Product {

  private final ProductId id;
  private Sku sku;
  private String name;
  private String description;
  private Barcode barcode;
  private Stock stock;
  private Money cost;
  private Money price;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private BrandId brandId;

  private Map<PropertyId, PropertyValue> properties;

  private Set<CategoryId> categoryIds;

  public Product(ProductId id, Sku sku, String name, Money cost, Money price) {
    this.id = id;
    this.sku = sku;
    this.name = validateName(name);
    validatePricing(cost, price);
    this.cost = cost;
    this.price = price;
    this.stock = Stock.empty();
    this.categoryIds = new HashSet<>();
    this.properties = new HashMap<>();
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  public Product(
      ProductId id,
      Sku sku,
      String name,
      String description,
      Barcode barcode,
      Money cost,
      Money price,
      Stock stock,
      BrandId brandId,
      Set<CategoryId> categoryIds,
      Map<PropertyId, PropertyValue> properties,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.sku = sku;
    this.name = validateName(name);
    this.description = description;
    this.barcode = barcode;
    validatePricing(cost, price);
    this.cost = cost;
    this.price = price;
    this.stock = stock;
    this.brandId = brandId;
    this.categoryIds = new HashSet<>(categoryIds);
    this.properties = new HashMap<>(properties);
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public void increaseStock(int amount) {
    this.stock = this.stock.increase(amount);
    this.updatedAt = LocalDateTime.now();
  }

  public void decreaseStock(int amount) {
    this.stock = this.stock.decrease(amount);
    this.updatedAt = LocalDateTime.now();
  }

  public void setStock(int amount) {
    this.stock = new Stock(amount);
    this.updatedAt = LocalDateTime.now();
  }

  public boolean hasStock() {
    return this.stock.isAvailable();
  }

  public boolean hasSufficientStock(int requiredAmount) {
    return this.stock.isSufficient(requiredAmount);
  }

  private String validateName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Product name cannot be null or empty");
    }
    String trimmed = name.trim();
    if (trimmed.length() > 100) {
      throw new IllegalArgumentException("Product name cannot exceed 100 characters");
    }
    return trimmed;
  }

  private void validatePricing(Money cost, Money price) {
    if (price.isGreaterThan(cost)) {
      return;
    }

    throw new IllegalArgumentException("Price must be greater than cost");
  }

  public ProductId getId() {
    return id;
  }

  public Sku getSku() {
    return sku;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Barcode getBarcode() {
    return barcode;
  }

  public Stock getStock() {
    return stock;
  }

  public Money getCost() {
    return cost;
  }

  public Money getPrice() {
    return price;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public BrandId getBrandId() {
    return brandId;
  }

  public Set<CategoryId> getCategoryIds() {
    return categoryIds;
  }

  public Map<PropertyId, PropertyValue> getProperties() {
    return properties;
  }

}
