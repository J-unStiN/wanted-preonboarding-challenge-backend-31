package com.ex.backend.domain.sellers.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sellers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SellersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

//    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    private LocalDateTime createdAt;



    public SellersEntity(String name, String description, String logoUrl, BigDecimal rating,
                       String contactEmail, String contactPhone) {
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.rating = rating;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    // 업데이트 메서드
    public void update(String name, String description, String logoUrl, BigDecimal rating,
                     String contactEmail, String contactPhone) {
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.rating = rating;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }
}