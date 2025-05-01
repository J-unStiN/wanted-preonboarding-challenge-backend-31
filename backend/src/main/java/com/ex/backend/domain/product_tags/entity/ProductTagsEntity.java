package com.ex.backend.domain.product_tags.entity;

import com.ex.backend.domain.products.entity.ProductsEntity;
import com.ex.backend.domain.tags.entity.TagsEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_tags")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductTagsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductsEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private TagsEntity tag;

    public ProductTagsEntity(ProductsEntity product, TagsEntity tag) {
        this.product = product;
        this.tag = tag;
    }

    public void update(ProductsEntity product, TagsEntity tag) {
        this.product = product;
        this.tag = tag;
    }
}