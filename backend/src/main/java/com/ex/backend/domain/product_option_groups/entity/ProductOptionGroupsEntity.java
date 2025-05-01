package com.ex.backend.domain.product_option_groups.entity;

import com.ex.backend.common.BaseEntity;
import com.ex.backend.domain.products.entity.ProductsEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_option_groups")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionGroupsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductsEntity product;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "display_order", columnDefinition = "INTEGER DEFAULT 0")
    private Integer displayOrder;

    public ProductOptionGroupsEntity(ProductsEntity product, String name, Integer displayOrder) {
        this.product = product;
        this.name = name;
        this.displayOrder = displayOrder != null ? displayOrder : 0;
    }

    public void update(String name, Integer displayOrder) {
        this.name = name;
        this.displayOrder = displayOrder != null ? displayOrder : this.displayOrder;
    }
}