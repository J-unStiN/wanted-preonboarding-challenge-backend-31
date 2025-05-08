package com.ex.backend.dto.products.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponseDto {

    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String primaryImageUrl;
    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private String brandName;
    private String primaryCategoryName;
    private Double averageRating;
    private Long reviewCount;
    private boolean inStock;
}
