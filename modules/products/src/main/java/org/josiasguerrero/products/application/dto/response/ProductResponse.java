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
    BrandPResponse brand,
    Set<CategoryPResponse> categories,
    Map<String, String> properties, // aquí ya usas nombre de propiedad como key
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
}
