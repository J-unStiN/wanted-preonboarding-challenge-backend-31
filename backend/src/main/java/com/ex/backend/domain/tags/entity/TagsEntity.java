package com.ex.backend.domain.tags.entity;

import com.ex.backend.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tags")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "slug", nullable = false, length = 100, unique = true)
    private String slug;

    public TagsEntity(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    public void update(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }
}