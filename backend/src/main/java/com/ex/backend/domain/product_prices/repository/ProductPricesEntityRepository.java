package com.ex.backend.domain.product_prices.repository;

import com.ex.backend.domain.product_prices.entity.ProductPricesEntity;
import com.ex.backend.domain.products.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductPricesEntityRepository extends JpaRepository<ProductPricesEntity, Long> {
    List<ProductPricesEntity> findByProduct(ProductsEntity product);

}
