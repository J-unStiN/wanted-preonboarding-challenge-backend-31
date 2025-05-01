package com.ex.backend.domain.brands.repository;

import com.ex.backend.domain.brands.entity.BrandsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandsEntityRepository extends JpaRepository<BrandsEntity, Long> {
}