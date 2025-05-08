package com.ex.backend.domain.product_options.repository;

import com.ex.backend.domain.product_options.entity.ProductOptionsEntity;
import com.ex.backend.domain.products.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductOptionsEntityRepository extends JpaRepository<ProductOptionsEntity, Long> {
    @Query("SELECT COUNT(o) FROM ProductOptionsEntity o " +
            "WHERE o.optionGroup.product = :product AND o.stock > :minStock")
    Long countByOptionGroupProductAndStockGreaterThan(ProductsEntity product, int minStock);
}
