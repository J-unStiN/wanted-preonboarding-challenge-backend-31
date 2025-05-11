package com.ex.backend.dto.products.res;

import com.ex.backend.domain.brands.entity.BrandsEntity;
import com.ex.backend.domain.product_details.entity.ProductDetailsEntity;
import com.ex.backend.domain.product_details.repository.ProductDetailsEntityRepository;
import com.ex.backend.domain.products.entity.ProductsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    if (product.getBrand() == null) {
        return null;
    }

    BrandsEntity brand = product.getBrand();
    return BrandDto.builder()
            .id(brand.getId())
            .name(brand.getName())
            .description(brand.getDescription())
            .logoUrl(brand.getLogoUrl())
            .website(brand.getWebsite())
            .build();
}

private static DetailDto convertToDetailDto(ProductsEntity product) {
    // 상품 상세정보 엔티티가 있다고 가정
    ProductDetailsEntity detail = productDetailRepository.findByProductId(product.getId())
            .orElse(null);

    if (detail == null) {
        return null;
    }

    return DetailDto.builder()
            .weight(detail.getWeight())
            .dimensions(detail.getDimensions()) // JSON 객체로 변환 필요
            .materials(detail.getMaterials())
            .countryOfOrigin(detail.getCountryOfOrigin())
            .warrantyInfo(detail.getWarrantyInfo())
            .careInstructions(detail.getCareInstructions())
            .additionalInfo(detail.getAdditionalInfo()) // JSON 객체로 변환 필요
            .build();
}

private static PriceDto convertToPriceDto(ProductsEntity product) {
    // 상품 가격정보 엔티티가 있다고 가정
    ProductPriceEntity price = productPriceRepository.findByProductId(product.getId())
            .orElse(null);

    if (price == null) {
        return null;
    }

    // 할인율 계산
    Integer discountPercentage = null;
    if (price.getBasePrice() != null && price.getSalePrice() != null &&
            price.getBasePrice().compareTo(BigDecimal.ZERO) > 0) {
        BigDecimal discount = price.getBasePrice().subtract(price.getSalePrice());
        discountPercentage = discount.multiply(new BigDecimal(100))
                .divide(price.getBasePrice(), 0, RoundingMode.HALF_UP)
                .intValue();
    }

    return PriceDto.builder()
            .basePrice(price.getBasePrice())
            .salePrice(price.getSalePrice())
            .currency(price.getCurrency())
            .taxRate(price.getTaxRate())
            .discountPercentage(discountPercentage)
            .build();
}

private static List<CategoryDto> convertToCategoriesDto(ProductsEntity product) {
    // 상품-카테고리 매핑 정보 조회
    List<ProductCategoryEntity> productCategories =
            productCategoryRepository.findByProductId(product.getId());

    if (productCategories.isEmpty()) {
        return Collections.emptyList();
    }

    return productCategories.stream()
            .map(pc -> {
                CategoryEntity category = pc.getCategory();
                CategoryEntity parent = category.getParent();

                CategoryDto.ParentDto parentDto = null;
                if (parent != null) {
                    parentDto = CategoryDto.ParentDto.builder()
                            .id(parent.getId())
                            .name(parent.getName())
                            .slug(parent.getSlug())
                            .build();
                }

                return CategoryDto.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .slug(category.getSlug())
                        .isPrimary(pc.getIsPrimary())
                        .parent(parentDto)
                        .build();
            })
            .collect(Collectors.toList());
}

private static List<OptionGroupDto> convertToOptionGroupsDto(ProductsEntity product) {
    // 상품 옵션 그룹 조회
    List<ProductOptionGroupEntity> optionGroups =
            productOptionGroupRepository.findByProductIdOrderByDisplayOrder(product.getId());

    if (optionGroups.isEmpty()) {
        return Collections.emptyList();
    }

    return optionGroups.stream()
            .map(og -> {
                List<ProductOptionEntity> options =
                        productOptionRepository.findByOptionGroupIdOrderByDisplayOrder(og.getId());

                List<OptionDto> optionDtos = options.stream()
                        .map(o -> OptionDto.builder()
                                .id(o.getId())
                                .name(o.getName())
                                .additionalPrice(o.getAdditionalPrice())
                                .sku(o.getSku())
                                .stock(o.getStock())
                                .displayOrder(o.getDisplayOrder())
                                .build())
                        .collect(Collectors.toList());

                return OptionGroupDto.builder()
                        .id(og.getId())
                        .name(og.getName())
                        .displayOrder(og.getDisplayOrder())
                        .options(optionDtos)
                        .build();
            })
            .collect(Collectors.toList());
}

private static List<ImageDto> convertToImagesDto(ProductsEntity product) {
    // 상품 이미지 조회
    List<ProductImageEntity> images =
            productImageRepository.findByProductIdOrderByDisplayOrder(product.getId());

    if (images.isEmpty()) {
        return Collections.emptyList();
    }

    return images.stream()
            .map(img -> ImageDto.builder()
                    .id(img.getId())
                    .url(img.getUrl())
                    .altText(img.getAltText())
                    .isPrimary(img.getIsPrimary())
                    .displayOrder(img.getDisplayOrder())
                    .optionId(img.getOptionId())
                    .build())
            .collect(Collectors.toList());
}

private static List<TagDto> convertToTagsDto(ProductsEntity product) {
    // 상품-태그 매핑 정보 조회
    List<ProductTagEntity> productTags =
            productTagRepository.findByProductId(product.getId());

    if (productTags.isEmpty()) {
        return Collections.emptyList();
    }

    return productTags.stream()
            .map(pt -> {
                TagEntity tag = pt.getTag();
                return TagDto.builder()
                        .id(tag.getId())
                        .name(tag.getName())
                        .slug(tag.getSlug())
                        .build();
            })
            .collect(Collectors.toList());
}

private static RatingDto convertToRatingDto(ProductsEntity product) {
    // 상품 리뷰 정보 조회
    List<ReviewEntity> reviews =
            reviewRepository.findByProductId(product.getId());

    if (reviews.isEmpty()) {
        return null;
    }

    // 평균 평점 계산
    double averageRating = reviews.stream()
            .mapToInt(ReviewEntity::getRating)
            .average()
            .orElse(0.0);

    // 평점 분포 계산
    Map<Integer, Long> distribution = reviews.stream()
            .collect(Collectors.groupingBy(
                    ReviewEntity::getRating,
                    Collectors.counting()));

    // 1~5점 모두 포함되도록 기본값 설정
    Map<String, Integer> ratingDistribution = new HashMap<>();
    for (int i = 1; i <= 5; i++) {
        ratingDistribution.put(String.valueOf(i),
                distribution.getOrDefault(i, 0L).intValue());
    }

    return RatingDto.builder()
            .average(BigDecimal.valueOf(averageRating)
                    .setScale(1, RoundingMode.HALF_UP).doubleValue())
            .count(reviews.size())
            .distribution(ratingDistribution)
            .build();
}

private static List<RelatedProductDto> convertToRelatedProductsDto(ProductsEntity product) {
    // 관련 상품 조회 로직 (같은 카테고리, 같은 브랜드 등)
    List<ProductsEntity> relatedProducts =
            productsRepository.findRelatedProducts(product.getId());

    if (relatedProducts.isEmpty()) {
        return Collections.emptyList();
    }

    return relatedProducts.stream()
            .map(rp -> {
                // 대표 이미지 조회
                ProductImageEntity primaryImage =
                        productImageRepository.findFirstByProductIdAndIsPrimaryTrueOrderByDisplayOrder(
                                rp.getId()).orElse(null);

                // 가격 정보 조회
                ProductPriceEntity price =
                        productPriceRepository.findByProductId(rp.getId()).orElse(null);

                ImageDto primaryImageDto = null;
                if (primaryImage != null) {
                    primaryImageDto = ImageDto.builder()
                            .url(primaryImage.getUrl())
                            .altText(primaryImage.getAltText())
                            .build();
                }

                return RelatedProductDto.builder()
                        .id(rp.getId())
                        .name(rp.getName())
                        .slug(rp.getSlug())
                        .shortDescription(rp.getShortDescription())
                        .primaryImage(primaryImageDto)
                        .basePrice(price != null ? price.getBasePrice() : null)
                        .salePrice(price != null ? price.getSalePrice() : null)
                        .currency(price != null ? price.getCurrency() : null)
                        .build();
            })
            .collect(Collectors.toList());
}
}
