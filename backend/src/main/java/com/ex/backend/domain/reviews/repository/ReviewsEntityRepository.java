package com.ex.backend.domain.reviews.repository;

import com.ex.backend.domain.reviews.entity.ReviewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsEntityRepository extends JpaRepository<ReviewsEntity, Long> {
}
