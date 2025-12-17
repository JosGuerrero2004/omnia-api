package org.josiasguerrero.products.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public record ProductResponse(
    String id,
    String sku,
    String name,
    String description,
    String barcode,
    BigDecimal cost,
    BigDecimal price,
    Integer stock,
    String brandId,
    Set<Integer> categoryIds,
    Map<Integer, String> properties,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
}
