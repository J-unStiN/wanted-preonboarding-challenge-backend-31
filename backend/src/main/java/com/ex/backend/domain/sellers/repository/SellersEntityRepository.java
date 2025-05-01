package com.ex.backend.domain.sellers.repository;

import com.ex.backend.domain.sellers.entity.SellersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellersEntityRepository extends JpaRepository<SellersEntity, Long> {
}
