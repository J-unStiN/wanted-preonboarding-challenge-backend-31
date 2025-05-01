package com.ex.backend.domain.products.entity;

import com.ex.backend.common.BaseEntity;
import com.ex.backend.domain.brands.entity.BrandsEntity;
import com.ex.backend.domain.sellers.entity.SellersEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ProductsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "slug", nullable = false, length = 255, unique = true)
    private String slug;

    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Column(name = "full_description", columnDefinition = "TEXT")
    private String fullDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private SellersEntity seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private BrandsEntity brand;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    public ProductsEntity(String name, String slug, String shortDescription, String fullDescription,
                       SellersEntity seller, BrandsEntity brand, String status) {
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.seller = seller;
        this.brand = brand;
        this.status = status;
    }

    public void update(String name, String slug, String shortDescription, String fullDescription,
                     SellersEntity seller, BrandsEntity brand, String status) {
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.seller = seller;
        this.brand = brand;
        this.status = status;
    }
}