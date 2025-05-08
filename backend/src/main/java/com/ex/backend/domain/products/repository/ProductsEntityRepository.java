package com.ex.backend.domain.products.repository;

import com.ex.backend.domain.products.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductsEntityRepository extends JpaRepository<ProductsEntity, Long>, JpaSpecificationExecutor<ProductsEntity> {

}
