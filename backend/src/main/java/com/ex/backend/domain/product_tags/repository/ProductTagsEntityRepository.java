package com.ex.backend.domain.product_tags.repository;

import com.ex.backend.domain.product_tags.entity.ProductTagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagsEntityRepository extends JpaRepository<ProductTagsEntity, Long> {
}
