package com.ex.backend.domain.product_details.repository;

import com.ex.backend.domain.product_details.entity.ProductDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailsEntityRepository extends JpaRepository<ProductDetailsEntity, Long> {
}
