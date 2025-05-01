package com.ex.backend.domain.categories.repository;

import com.ex.backend.domain.categories.entity.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesEntityRepository extends JpaRepository<CategoriesEntity, Long> {
}
