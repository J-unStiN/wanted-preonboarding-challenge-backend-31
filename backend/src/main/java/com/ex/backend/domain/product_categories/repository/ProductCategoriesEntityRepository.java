package com.ex.backend.domain.product_categories.repository;

import com.ex.backend.domain.product_categories.entity.ProductCategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoriesEntityRepository extends JpaRepository<ProductCategoriesEntity, Long> {
}
