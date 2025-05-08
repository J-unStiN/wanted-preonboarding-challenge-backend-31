package com.ex.backend.domain.product_images.repository;


import com.ex.backend.domain.product_images.entity.ProductImagesEntity;
import com.ex.backend.domain.products.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImagesEntityRepository extends JpaRepository<ProductImagesEntity, Long> {

    List<ProductImagesEntity> findByProductAndIsPrimaryTrue(ProductsEntity product);
    List<ProductImagesEntity> findFirstByProduct(ProductsEntity product);
}
