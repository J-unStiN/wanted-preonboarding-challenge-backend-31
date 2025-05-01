package com.ex.backend.domain.product_categories.entity;

import com.ex.backend.common.BaseEntity;
import com.ex.backend.domain.categories.entity.CategoriesEntity;
import com.ex.backend.domain.products.entity.ProductsEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategoriesEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductsEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoriesEntity category;

    @Column(name = "is_primary", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPrimary;

    public ProductCategoriesEntity(ProductsEntity product, CategoriesEntity category, Boolean isPrimary) {
        this.product = product;
        this.category = category;
        this.isPrimary = isPrimary != null ? isPrimary : false;
    }

    public void update(CategoriesEntity category, Boolean isPrimary) {
        this.category = category;
        this.isPrimary = isPrimary != null ? isPrimary : this.isPrimary;
    }
}