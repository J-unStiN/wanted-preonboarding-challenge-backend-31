package com.ex.backend.dto.products.res;

import com.ex.backend.domain.products.entity.ProductsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponseDto {
    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private SellerDto seller;
    private BrandDto brand;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private DetailDto detail;
    private PriceDto price;
    private List<CategoryDto> categories;
    private List<OptionGroupDto> optionGroups;
    private List<ImageDto> images;
    private List<TagDto> tags;
    private RatingDto rating;
    private List<RelatedProductDto> relatedProducts;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SellerDto {
        private Long id;
        private String name;
        private String description;
        private String logoUrl;
        private Double rating;
        private String contactEmail;
        private String contactPhone;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandDto {
        private Long id;
        private String name;
        private String description;
        private String logoUrl;
        private String website;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailDto {
        private Double weight;
        private Map<String, Integer> dimensions;
        private String materials;
        private String countryOfOrigin;
        private String warrantyInfo;
        private String careInstructions;
        private Map<String, Object> additionalInfo;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PriceDto {
        private BigDecimal basePrice;
        private BigDecimal salePrice;
        private String currency;
        private Integer taxRate;
        private Integer discountPercentage;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryDto {
        private Long id;
        private String name;
        private String slug;
        private boolean isPrimary;
        private ParentCategoryDto parent;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParentCategoryDto {
        private Long id;
        private String name;
        private String slug;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionGroupDto {
        private Long id;
        private String name;
        private Integer displayOrder;
        private List<OptionDto> options;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionDto {
        private Long id;
        private String name;
        private BigDecimal additionalPrice;
        private String sku;
        private Integer stock;
        private Integer displayOrder;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageDto {
        private Long id;
        private String url;
        private String altText;
        private boolean isPrimary;
        private Integer displayOrder;
        private Long optionId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TagDto {
        private Long id;
        private String name;
        private String slug;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RatingDto {
        private Double average;
        private Long count;
        private Map<String, Integer> distribution;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RelatedProductDto {
        private Long id;
        private String name;
        private String slug;
        private String shortDescription;
        private ImageDto primaryImage;
        private BigDecimal basePrice;
        private BigDecimal salePrice;
        private String currency;
    }

    // ProductsEntity에서 ProductDetailResponseDto로 변환하는 정적 메서드
    public static ProductDetailResponseDto fromEntity(ProductsEntity product) {
        return ProductDetailResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .fullDescription(product.getFullDescription())
                .seller(convertToSellerDto(product))
                .brand(convertToBrandDto(product))
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .detail(convertToDetailDto(product))
                .price(convertToPriceDto(product))
                .categories(convertToCategoriesDto(product))
                .optionGroups(convertToOptionGroupsDto(product))
                .images(convertToImagesDto(product))
                .tags(convertToTagsDto(product))
                .rating(convertToRatingDto(product))
                .relatedProducts(convertToRelatedProductsDto(product))
                .build();
    }

    // 각 변환 메서드는 실제 구현 시 추가해야 합니다
    private static SellerDto convertToSellerDto(ProductsEntity product) {
        if (product.getSeller() == null) return null;

        return SellerDto.builder()
                .id(product.getSeller().getId())
                .name(product.getSeller().getName())
                .description(product.getSeller().getDescription())
                .logoUrl(product.getSeller().getLogoUrl())
                .rating(product.getSeller().getRating().doubleValue())
                .contactEmail(product.getSeller().getContactEmail())
                .contactPhone(product.getSeller().getContactPhone())
                .build();
    }

    private static BrandDto convertToBrandDto(ProductsEntity product) {
        // 브랜드 정보 변환 로직 구현
        // 실제 구현에서 완성 필요
        return null;
    }

    // 나머지 변환 메서드들은 실제 구현 시 추가...
    private static DetailDto convertToDetailDto(ProductsEntity product) {
        return null; // 실제 구현 필요
    }

    private static PriceDto convertToPriceDto(ProductsEntity product) {
        return null; // 실제 구현 필요
    }

    private static List<CategoryDto> convertToCategoriesDto(ProductsEntity product) {
        return null; // 실제 구현 필요
    }

    private static List<OptionGroupDto> convertToOptionGroupsDto(ProductsEntity product) {
        return null; // 실제 구현 필요
    }

    private static List<ImageDto> convertToImagesDto(ProductsEntity product) {
        return null; // 실제 구현 필요
    }

    private static List<TagDto> convertToTagsDto(ProductsEntity product) {
        return null; // 실제 구현 필요
    }

    private static RatingDto convertToRatingDto(ProductsEntity product) {
        return null; // 실제 구현 필요
    }

    private static List<RelatedProductDto> convertToRelatedProductsDto(ProductsEntity product) {
        return null; // 실제 구현 필요
    }
}
