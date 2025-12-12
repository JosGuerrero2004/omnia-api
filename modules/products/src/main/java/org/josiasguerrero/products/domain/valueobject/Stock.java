package org.josiasguerrero.products.domain.valueobject;

public record Stock(int quantity) {
  public Stock {
    if (quantity < 0) {
      throw new IllegalArgumentException("Stock must be greater than zero");
    }
  }

  public static Stock empty() {
    return new Stock(0);
  }

  public static Stock of(int quantity) {
    return new Stock(quantity);
  }

  public Stock increase(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Decrease amount must be positive: " + amount);
    }

    if (this.quantity < amount) {
      throw new IllegalStateException("Insufficient stock. Available: " + this.quantity + ", Required: " + amount);
    }
    return new Stock(this.quantity - amount);
  }

  public Stock decrease(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException(
          "Decrease amount must be positive: " + amount);
    }
    if (this.quantity < amount) {
      throw new IllegalStateException(
          "Insufficient stock. Available: " + this.quantity +
              ", Required: " + amount);
    }
    return new Stock(this.quantity - amount);
  }

  public boolean isAvailable() {
    return this.quantity > 0;
  }

  public boolean isEmpty() {
    return this.quantity == 0;
  }

  public boolean isSufficient(int requiredAmount) {
    return this.quantity >= requiredAmount;
  }

  public boolean isLow(int threshold) {
    return this.quantity <= threshold && this.quantity > 0;
  }
}
