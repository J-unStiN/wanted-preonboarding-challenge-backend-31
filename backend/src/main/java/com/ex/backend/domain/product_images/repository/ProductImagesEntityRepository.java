package com.ex.backend.domain.product_images.repository;


import com.ex.backend.domain.product_images.entity.ProductImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImagesEntityRepository extends JpaRepository<ProductImagesEntity, Long> {
}
