package com.ex.backend.service.products;

import com.ex.backend.domain.brands.entity.BrandsEntity;
import com.ex.backend.domain.brands.repository.BrandsEntityRepository;
import com.ex.backend.domain.categories.entity.CategoriesEntity;
import com.ex.backend.domain.categories.repository.CategoriesEntityRepository;
import com.ex.backend.domain.product_categories.entity.ProductCategoriesEntity;
import com.ex.backend.domain.product_categories.repository.ProductCategoriesEntityRepository;
import com.ex.backend.domain.product_details.entity.ProductDetailsEntity;
import com.ex.backend.domain.product_details.repository.ProductDetailsEntityRepository;
import com.ex.backend.domain.product_images.entity.ProductImagesEntity;
import com.ex.backend.domain.product_images.repository.ProductImagesEntityRepository;
import com.ex.backend.domain.product_option_groups.entity.ProductOptionGroupsEntity;
import com.ex.backend.domain.product_option_groups.repository.ProductOptionGroupsEntityRepository;
import com.ex.backend.domain.product_options.entity.ProductOptionsEntity;
import com.ex.backend.domain.product_options.repository.ProductOptionsEntityRepository;
import com.ex.backend.domain.product_prices.entity.ProductPricesEntity;
import com.ex.backend.domain.product_prices.repository.ProductPricesEntityRepository;
import com.ex.backend.domain.product_tags.entity.ProductTagsEntity;
import com.ex.backend.domain.product_tags.repository.ProductTagsEntityRepository;
import com.ex.backend.domain.products.entity.ProductsEntity;
import com.ex.backend.domain.products.repository.ProductsEntityRepository;
import com.ex.backend.domain.sellers.entity.SellersEntity;
import com.ex.backend.domain.sellers.repository.SellersEntityRepository;
import com.ex.backend.domain.tags.entity.TagsEntity;
import com.ex.backend.domain.tags.repository.TagsEntityRepository;
import com.ex.backend.dto.products.req.ProductRequestDto;
import com.ex.backend.dto.products.res.ProductResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductsEntityRepository productsRepository;
    private final ProductDetailsEntityRepository productDetailsRepository;
    private final ProductPricesEntityRepository productPricesRepository;
    private final ProductCategoriesEntityRepository productCategoriesRepository;
    private final ProductOptionGroupsEntityRepository productOptionGroupsRepository;
    private final ProductOptionsEntityRepository productOptionsRepository;
    private final ProductImagesEntityRepository productImagesRepository;
    private final ProductTagsEntityRepository productTagsRepository;
    private final BrandsEntityRepository brandsRepository;
    private final SellersEntityRepository sellersRepository;
    private final CategoriesEntityRepository categoriesRepository;
    private final TagsEntityRepository tagsRepository;
    private final ObjectMapper objectMapper;


    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        // 1. 관련 엔티티 조회
        SellersEntity seller = sellersRepository.findById(requestDto.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        BrandsEntity brand = brandsRepository.findById(requestDto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        // 2. 상품 기본 정보 저장
        ProductsEntity product = ProductsEntity.builder()
                .name(requestDto.getName())
                .slug(requestDto.getSlug())
                .shortDescription(requestDto.getShortDescription())
                .fullDescription(requestDto.getFullDescription())
                .seller(seller)
                .brand(brand)
                .status(requestDto.getStatus())
                .build();



        productsRepository.save(product);

        // 3. 상품 상세 정보 저장
        if (requestDto.getDetail() != null) {
            ProductDetailsEntity detail = ProductDetailsEntity.builder()
                    .product(product)
                    .weight(BigDecimal.valueOf(requestDto.getDetail().getWeight()))
                    .dimensions(convertMapToString(requestDto.getDetail().getDimensions()))
                    .materials(requestDto.getDetail().getMaterials())
                    .countryOfOrigin(requestDto.getDetail().getCountryOfOrigin())
                    .warrantyInfo(requestDto.getDetail().getWarrantyInfo())
                    .careInstructions(requestDto.getDetail().getCareInstructions())
                    .additionalInfo(convertMapToString(requestDto.getDetail().getAdditionalInfo()))
                    .build();

            productDetailsRepository.save(detail);
        }

        // 4. 상품 가격 정보 저장
        if (requestDto.getPrice() != null) {
            ProductPricesEntity price = ProductPricesEntity.builder()
                    .product(product)
                    .basePrice(BigDecimal.valueOf(requestDto.getPrice().getBasePrice()))
                    .salePrice(BigDecimal.valueOf(requestDto.getPrice().getSalePrice()))
                    .costPrice(BigDecimal.valueOf(requestDto.getPrice().getCostPrice()))
                    .currency(requestDto.getPrice().getCurrency())
                    .taxRate(BigDecimal.valueOf(requestDto.getPrice().getTaxRate()))
                    .build();

            productPricesRepository.save(price);
        }

        // 5. 상품 카테고리 연결
        if (requestDto.getCategories() != null) {
            List<ProductCategoriesEntity> productCategories = requestDto.getCategories().stream()
                    .map(categoryDto -> {
                        CategoriesEntity category = categoriesRepository.findById(categoryDto.getCategoryId())
                                .orElseThrow(() -> new RuntimeException("Category not found"));

                        return ProductCategoriesEntity.builder()
                                .product(product)
                                .category(category)
                                .isPrimary(categoryDto.getIsPrimary())
                                .build();
                    })
                    .collect(Collectors.toList());

            productCategoriesRepository.saveAll(productCategories);
        }

        // 6. 상품 옵션 그룹 및 옵션 저장
        if (requestDto.getOptionGroups() != null) {
            requestDto.getOptionGroups().forEach(groupDto -> {
                ProductOptionGroupsEntity optionGroup = ProductOptionGroupsEntity.builder()
                        .product(product)
                        .name(groupDto.getName())
                        .displayOrder(groupDto.getDisplayOrder())
                        .build();

                productOptionGroupsRepository.save(optionGroup);

                if (groupDto.getOptions() != null) {
                    List<ProductOptionsEntity> options = groupDto.getOptions().stream()
                            .map(optionDto -> ProductOptionsEntity.builder()
                                    .optionGroup(optionGroup)
                                    .name(optionDto.getName())
                                    .additionalPrice(BigDecimal.valueOf(optionDto.getAdditionalPrice()))
                                    .sku(optionDto.getSku())
                                    .stock(optionDto.getStock())
                                    .displayOrder(optionDto.getDisplayOrder())
                                    .build())
                            .collect(Collectors.toList());

                    productOptionsRepository.saveAll(options);
                }
            });
        }

        // 7. 상품 이미지 저장
        if (requestDto.getImages() != null) {
            List<ProductImagesEntity> images = requestDto.getImages().stream()
                    .map(imageDto -> ProductImagesEntity.builder()
                            .product(product)
                            .url(imageDto.getUrl())
                            .altText(imageDto.getAltText())
                            .isPrimary(imageDto.getIsPrimary())
                            .displayOrder(imageDto.getDisplayOrder())
                            .build())
                    .collect(Collectors.toList());

            productImagesRepository.saveAll(images);
        }

        // 8. 상품 태그 연결
        if (requestDto.getTags() != null) {
            List<ProductTagsEntity> productTags = requestDto.getTags().stream()
                    .map(tagId -> {
                        TagsEntity tag = tagsRepository.findById(tagId)
                                .orElseThrow(() -> new RuntimeException("Tag not found"));

                        return new ProductTagsEntity(product, tag);
                    })
                    .collect(Collectors.toList());

            productTagsRepository.saveAll(productTags);
        }

        // 9. 응답 DTO 생성
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    // Map을 JSON 문자열로 변환하는 유틸리티 메서드
    private String convertMapToString(Map<String, Object> map) {
        if (map == null) return null;
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            throw new RuntimeException("Error converting map to string", e);
        }
    }

    // 여기에 다른 상품 관련 서비스 메서드들이 들어갑니다.


}
