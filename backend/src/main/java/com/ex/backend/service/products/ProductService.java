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
import com.ex.backend.dto.products.req.PageResponse;
import com.ex.backend.dto.products.req.ProductRequestDto;
import com.ex.backend.dto.products.res.ProductListResponseDto;
import com.ex.backend.dto.products.res.ProductResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Transactional(readOnly = true)
    public PageResponse<ProductListResponseDto> getProducts(
            int page, int perPage, String sort, String status,
            Integer minPrice, Integer maxPrice, String category,
            Long seller, Long brand, Boolean inStock, String search) {

        // 정렬 처리
        Sort sortObj = createSort(sort);

        // 페이지네이션 처리
        Pageable pageable = PageRequest.of(page - 1, perPage, sortObj);

        // 필터링 처리
        Specification<ProductsEntity> spec = createSpecification(
                status, minPrice, maxPrice, category, seller, brand, inStock, search);

        // 상품 조회
        Page<ProductsEntity> productPage = productsRepository.findAll(spec, pageable);

        // 응답 DTO 변환
        List<ProductListResponseDto> products = productPage.getContent().stream()
                .map(this::convertToProductListDto)
                .collect(Collectors.toList());

        // 페이지네이션 정보 설정
        PageResponse.PageInfo pageInfo = PageResponse.PageInfo.builder()
                .currentPage(page)
                .totalPages(productPage.getTotalPages())
                .totalItems(productPage.getTotalElements())
                .perPage(perPage)
                .build();

        return PageResponse.<ProductListResponseDto>builder()
                .data(products)
                .paging(pageInfo)
                .build();
    }

    private Sort createSort(String sortStr) {
        String[] parts = sortStr.split(":");
        String field = parts[0].replace("_", "");

        // 카멜케이스로 변환
        if (field.equals("createdat")) field = "createdAt";
        if (field.equals("updatedat")) field = "updatedAt";

        Sort.Direction direction = parts.length > 1 && parts[1].equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return Sort.by(direction, field);
    }

    private Specification<ProductsEntity> createSpecification(
            String status, Integer minPrice, Integer maxPrice, String category,
            Long seller, Long brand, Boolean inStock, String search) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 상태 필터
            if (status != null && !status.isEmpty()) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            // 판매자 필터
            if (seller != null) {
                predicates.add(cb.equal(root.get("seller").get("id"), seller));
            }

            // 브랜드 필터
            if (brand != null) {
                predicates.add(cb.equal(root.get("brand").get("id"), brand));
            }

            // 가격 범위 필터
            if (minPrice != null || maxPrice != null) {
                Join<Object, Object> priceJoin = root.join("productPrices", JoinType.LEFT);

                if (minPrice != null) {
                    predicates.add(cb.greaterThanOrEqualTo(
                            priceJoin.get("salePrice"), BigDecimal.valueOf(minPrice)));
                }

                if (maxPrice != null) {
                    predicates.add(cb.lessThanOrEqualTo(
                            priceJoin.get("salePrice"), BigDecimal.valueOf(maxPrice)));
                }
            }

            // 카테고리 필터
            if (category != null && !category.isEmpty()) {
                Join<Object, Object> categoryJoin = root.join("productCategories", JoinType.LEFT);
                Join<Object, Object> categoryEntityJoin = categoryJoin.join("category", JoinType.LEFT);

                try {
                    // ID로 검색
                    Long categoryId = Long.parseLong(category);
                    predicates.add(cb.equal(categoryEntityJoin.get("id"), categoryId));
                } catch (NumberFormatException e) {
                    // 슬러그로 검색
                    predicates.add(cb.equal(categoryEntityJoin.get("slug"), category));
                }
            }

            // 재고 여부 필터
            if (inStock != null && inStock) {
                Join<Object, Object> optionGroupJoin = root.join("productOptionGroups", JoinType.LEFT);
                Join<Object, Object> optionJoin = optionGroupJoin.join("productOptions", JoinType.LEFT);

                predicates.add(cb.greaterThan(optionJoin.get("stock"), 0));
                query.distinct(true);
            }

            // 검색 기능
            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";

                Predicate namePredicate = cb.like(cb.lower(root.get("name")), searchPattern);
                Predicate descPredicate = cb.like(cb.lower(root.get("shortDescription")), searchPattern);
                Predicate fullDescPredicate = cb.like(cb.lower(root.get("fullDescription")), searchPattern);

                // 브랜드명 검색
                Join<Object, Object> brandJoin = root.join("brand", JoinType.LEFT);
                Predicate brandPredicate = cb.like(cb.lower(brandJoin.get("name")), searchPattern);

                // 태그명 검색
                Join<Object, Object> tagJoin = root.join("productTags", JoinType.LEFT);
                Join<Object, Object> tagEntityJoin = tagJoin.join("tag", JoinType.LEFT);
                Predicate tagPredicate = cb.like(cb.lower(tagEntityJoin.get("name")), searchPattern);

                // 카테고리명 검색
                Join<Object, Object> categoryJoin = root.join("productCategories", JoinType.LEFT);
                Join<Object, Object> categoryEntityJoin = categoryJoin.join("category", JoinType.LEFT);
                Predicate categoryPredicate = cb.like(cb.lower(categoryEntityJoin.get("name")), searchPattern);

                predicates.add(cb.or(
                        namePredicate, descPredicate, fullDescPredicate,
                        brandPredicate, tagPredicate, categoryPredicate
                ));
                query.distinct(true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private ProductListResponseDto convertToProductListDto(ProductsEntity product) {
        // DB에서 조회한 엔티티를 DTO로 변환
        // 실제 구현에서는 옵션 재고, 평균 평점, 리뷰 수 등 추가 정보도 함께 조회해야 함

        return ProductListResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .primaryImageUrl(getPrimaryImageUrl(product))
                .basePrice(getBasePrice(product))
                .salePrice(getSalePrice(product))
                .brandName(product.getBrand() != null ? product.getBrand().getName() : null)
                .primaryCategoryName(getPrimaryCategoryName(product))
                .averageRating(getAverageRating(product))
                .reviewCount(getReviewCount(product))
                .inStock(isInStock(product))
                .build();
    }

    // 헬퍼 메서드들 (실제 구현 필요)
    private String getPrimaryImageUrl(ProductsEntity product) {
        List<ProductImagesEntity> primaryImages = productImagesRepository.findByProductAndIsPrimaryTrue(product);
        if (!primaryImages.isEmpty()) {
            return primaryImages.getFirst().getUrl();
        }

        List<ProductImagesEntity> anyImages = productImagesRepository.findFirstByProduct(product);
        if (!anyImages.isEmpty()) {
            return anyImages.getFirst().getUrl();
        }

        return null;
    }
    private BigDecimal getBasePrice(ProductsEntity product) {
        return productPricesRepository.findByProduct(product)
                .stream()
                .filter(price -> price.getProduct().getId().equals(product.getId()))
                .findFirst()
                .map(ProductPricesEntity::getBasePrice)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getSalePrice(ProductsEntity product) {
        return productPricesRepository.findByProduct(product)
                .stream()
                .filter(price -> price.getProduct().getId().equals(product.getId()))
                .findFirst()
                .map(ProductPricesEntity::getSalePrice)
                .orElse(BigDecimal.ZERO);
    }

    private String getPrimaryCategoryName(ProductsEntity product) {
        return productCategoriesRepository.findByProductAndIsPrimaryTrue(product)
                .stream()
                .findFirst()
                .map(pc -> pc.getCategory().getName())
                .orElse(null);
    }

    private Double getAverageRating(ProductsEntity product) {
        // 실제 구현에서는 리뷰 테이블에서 평균 평점을 계산
        return 0.0;
    }

    private Long getReviewCount(ProductsEntity product) {
        // 실제 구현에서는 리뷰 개수 조회
        return 0L;
    }

    private boolean isInStock(ProductsEntity product) {
        // 실제 구현에서는 모든 옵션의 재고 확인
        return productOptionsRepository.countByOptionGroupProductAndStockGreaterThan(product, 0) > 0;
    }



}
