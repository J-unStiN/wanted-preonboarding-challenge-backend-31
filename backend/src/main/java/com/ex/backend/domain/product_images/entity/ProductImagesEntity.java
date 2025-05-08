package com.ex.backend.domain.product_images.entity;

import com.ex.backend.common.BaseEntity;
import com.ex.backend.domain.products.entity.ProductsEntity;
import com.ex.backend.domain.product_options.entity.ProductOptionsEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "product_images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductImagesEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductsEntity product;

    @Column(name = "url", nullable = false, length = 255)
    private String url;

    @Column(name = "alt_text", length = 255)
    private String altText;

    @Column(name = "is_primary", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPrimary;

    @Column(name = "display_order", columnDefinition = "INTEGER DEFAULT 0")
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private ProductOptionsEntity option;

//    public ProductImagesEntity(ProductsEntity product, String url, String altText,
//                            Boolean isPrimary, Integer displayOrder, ProductOptionsEntity option) {
//        this.product = product;
//        this.url = url;
//        this.altText = altText;
//        this.isPrimary = isPrimary != null ? isPrimary : false;
//        this.displayOrder = displayOrder != null ? displayOrder : 0;
//        this.option = option;
//    }

    public void update(String url, String altText, Boolean isPrimary,
                    Integer displayOrder, ProductOptionsEntity option) {
        this.url = url;
        this.altText = altText;
        this.isPrimary = isPrimary != null ? isPrimary : this.isPrimary;
        this.displayOrder = displayOrder != null ? displayOrder : this.displayOrder;
        this.option = option;
    }
}