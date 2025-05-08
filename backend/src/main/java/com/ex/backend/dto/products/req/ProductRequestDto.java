package com.ex.backend.dto.products.req;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private Long sellerId;
    private Long brandId;
    private String status;

    private ProductDetailDto detail;
    private ProductPriceDto price;
    private List<ProductCategoryDto> categories;
    private List<ProductOptionGroupDto> optionGroups;
    private List<ProductImageDto> images;
    private List<Long> tags;


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDetailDto {
        private Double weight;
        private Map<String, Object> dimensions;
        private String materials;
        private String countryOfOrigin;
        private String warrantyInfo;
        private String careInstructions;
        private Map<String, Object> additionalInfo;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductPriceDto {
        private Integer basePrice;
        private Integer salePrice;
        private Integer costPrice;
        private String currency;
        private Integer taxRate;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductCategoryDto {
        private Long categoryId;
        private Boolean isPrimary;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOptionGroupDto {
        private String name;
        private Integer displayOrder;
        private List<ProductOptionDto> options;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOptionDto {
        private String name;
        private Integer additionalPrice;
        private String sku;
        private Integer stock;
        private Integer displayOrder;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductImageDto {
        private String url;
        private String altText;
        private Boolean isPrimary;
        private Integer displayOrder;
        private Long optionId;
    }

}
