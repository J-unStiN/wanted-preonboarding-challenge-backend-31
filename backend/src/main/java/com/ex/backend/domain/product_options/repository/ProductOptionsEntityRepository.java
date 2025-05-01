package com.ex.backend.domain.product_options.repository;

import com.ex.backend.domain.product_options.entity.ProductOptionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionsEntityRepository extends JpaRepository<ProductOptionsEntity, Long> {
}
