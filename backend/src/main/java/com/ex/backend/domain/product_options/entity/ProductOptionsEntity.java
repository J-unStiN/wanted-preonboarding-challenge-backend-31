package com.ex.backend.domain.product_options.entity;

import com.ex.backend.domain.product_option_groups.entity.ProductOptionGroupsEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product_options")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroupsEntity optionGroup;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "additional_price", precision = 12, scale = 2, columnDefinition = "DECIMAL(12, 2) DEFAULT 0")
    private BigDecimal additionalPrice;

    @Column(name = "sku", length = 100)
    private String sku;

    @Column(name = "stock", columnDefinition = "INTEGER DEFAULT 0")
    private Integer stock;

    @Column(name = "display_order", columnDefinition = "INTEGER DEFAULT 0")
    private Integer displayOrder;

    public ProductOptionsEntity(ProductOptionGroupsEntity optionGroup, String name, BigDecimal additionalPrice,
                               String sku, Integer stock, Integer displayOrder) {
        this.optionGroup = optionGroup;
        this.name = name;
        this.additionalPrice = additionalPrice != null ? additionalPrice : BigDecimal.ZERO;
        this.sku = sku;
        this.stock = stock != null ? stock : 0;
        this.displayOrder = displayOrder != null ? displayOrder : 0;
    }

    public void update(String name, BigDecimal additionalPrice, String sku, Integer stock, Integer displayOrder) {
        this.name = name;
        this.additionalPrice = additionalPrice != null ? additionalPrice : this.additionalPrice;
        this.sku = sku;
        this.stock = stock != null ? stock : this.stock;
        this.displayOrder = displayOrder != null ? displayOrder : this.displayOrder;
    }
}