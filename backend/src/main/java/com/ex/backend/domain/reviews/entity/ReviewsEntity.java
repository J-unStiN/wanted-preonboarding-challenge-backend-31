package com.ex.backend.domain.reviews.entity;

import com.ex.backend.common.BaseEntity;
import com.ex.backend.domain.products.entity.ProductsEntity;
import com.ex.backend.domain.users.entity.UsersEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductsEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UsersEntity user;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "verified_purchase", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean verifiedPurchase;

    @Column(name = "helpful_votes", columnDefinition = "INTEGER DEFAULT 0")
    private Integer helpfulVotes;

    public ReviewsEntity(ProductsEntity product, UsersEntity user, Integer rating, String title,
                        String content, Boolean verifiedPurchase) {
        this.product = product;
        this.user = user;
        this.rating = rating;
        this.title = title;
        this.content = content;
        this.verifiedPurchase = verifiedPurchase != null ? verifiedPurchase : false;
        this.helpfulVotes = 0;
    }

    public void update(Integer rating, String title, String content, Boolean verifiedPurchase) {
        this.rating = rating;
        this.title = title;
        this.content = content;
        this.verifiedPurchase = verifiedPurchase != null ? verifiedPurchase : this.verifiedPurchase;
    }

    public void incrementHelpfulVotes() {
        this.helpfulVotes++;
    }
}