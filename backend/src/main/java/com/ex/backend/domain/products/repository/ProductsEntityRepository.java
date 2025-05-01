package com.ex.backend.domain.products.repository;

import com.ex.backend.domain.products.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsEntityRepository extends JpaRepository<ProductsEntity, Long> {
}
