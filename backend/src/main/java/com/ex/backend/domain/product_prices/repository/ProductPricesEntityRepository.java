package com.ex.backend.domain.product_prices.repository;

import com.ex.backend.domain.product_prices.entity.ProductPricesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPricesEntityRepository extends JpaRepository<ProductPricesEntity, Long> {
}
