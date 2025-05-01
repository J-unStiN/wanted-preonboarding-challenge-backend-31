package com.ex.backend.domain.product_prices.entity;

import com.ex.backend.domain.products.entity.ProductsEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product_prices")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPricesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductsEntity product;

    @Column(name = "base_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "sale_price", precision = 12, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "cost_price", precision = 12, scale = 2)
    private BigDecimal costPrice;

    @Column(name = "currency", length = 3, columnDefinition = "VARCHAR(3) DEFAULT 'KRW'")
    private String currency;

    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate;

    public ProductPricesEntity(ProductsEntity product, BigDecimal basePrice, BigDecimal salePrice,
                            BigDecimal costPrice, String currency, BigDecimal taxRate) {
        this.product = product;
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.currency = currency != null ? currency : "KRW";
        this.taxRate = taxRate;
    }

    public void update(BigDecimal basePrice, BigDecimal salePrice, BigDecimal costPrice,
                     String currency, BigDecimal taxRate) {
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.currency = currency != null ? currency : this.currency;
        this.taxRate = taxRate;
    }
}