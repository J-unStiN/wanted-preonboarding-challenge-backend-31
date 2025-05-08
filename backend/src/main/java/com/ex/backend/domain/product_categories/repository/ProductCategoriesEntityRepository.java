package com.ex.backend.domain.product_categories.repository;

import com.ex.backend.domain.product_categories.entity.ProductCategoriesEntity;
import com.ex.backend.domain.products.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoriesEntityRepository extends JpaRepository<ProductCategoriesEntity, Long> {
    List<ProductCategoriesEntity> findByProductAndIsPrimaryTrue(ProductsEntity product);
}
