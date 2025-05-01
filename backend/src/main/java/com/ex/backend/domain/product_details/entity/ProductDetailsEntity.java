package com.ex.backend.domain.product_details.entity;

import com.ex.backend.common.BaseEntity;
import com.ex.backend.domain.products.entity.ProductsEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetailsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductsEntity product;

    @Column(name = "weight", precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(name = "dimensions", columnDefinition = "JSONB")
    private String dimensions;

    @Column(name = "materials", columnDefinition = "TEXT")
    private String materials;

    @Column(name = "country_of_origin", length = 100)
    private String countryOfOrigin;

    @Column(name = "warranty_info", columnDefinition = "TEXT")
    private String warrantyInfo;

    @Column(name = "care_instructions", columnDefinition = "TEXT")
    private String careInstructions;

    @Column(name = "additional_info", columnDefinition = "JSONB")
    private String additionalInfo;

    public ProductDetailsEntity(ProductsEntity product, BigDecimal weight, String dimensions,
                                String materials, String countryOfOrigin, String warrantyInfo,
                                String careInstructions, String additionalInfo) {
        this.product = product;
        this.weight = weight;
        this.dimensions = dimensions;
        this.materials = materials;
        this.countryOfOrigin = countryOfOrigin;
        this.warrantyInfo = warrantyInfo;
        this.careInstructions = careInstructions;
        this.additionalInfo = additionalInfo;
    }

    public void update(BigDecimal weight, String dimensions, String materials,
                       String countryOfOrigin, String warrantyInfo, String careInstructions,
                       String additionalInfo) {
        this.weight = weight;
        this.dimensions = dimensions;
        this.materials = materials;
        this.countryOfOrigin = countryOfOrigin;
        this.warrantyInfo = warrantyInfo;
        this.careInstructions = careInstructions;
        this.additionalInfo = additionalInfo;
    }
}