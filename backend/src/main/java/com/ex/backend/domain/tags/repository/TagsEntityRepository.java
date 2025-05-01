package com.ex.backend.domain.tags.repository;

import com.ex.backend.domain.tags.entity.TagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsEntityRepository extends JpaRepository<TagsEntity, Long> {
}
