package com.ex.backend.domain.product_option_groups.repository;

import com.ex.backend.domain.product_option_groups.entity.ProductOptionGroupsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionGroupsEntityRepository extends JpaRepository<ProductOptionGroupsEntity, Long> {
}
