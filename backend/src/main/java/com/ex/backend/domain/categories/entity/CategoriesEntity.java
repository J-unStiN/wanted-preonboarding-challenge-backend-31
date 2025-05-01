package com.ex.backend.domain.categories.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "slug", nullable = false, length = 100, unique = true)
    private String slug;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CategoriesEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoriesEntity> children = new ArrayList<>();

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    public CategoriesEntity(String name, String slug, String description, CategoriesEntity parent,
                         Integer level, String imageUrl) {
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.parent = parent;
        this.level = level;
        this.imageUrl = imageUrl;
    }

    public void update(String name, String slug, String description, CategoriesEntity parent,
                     Integer level, String imageUrl) {
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.parent = parent;
        this.level = level;
        this.imageUrl = imageUrl;
    }

    public void addChild(CategoriesEntity child) {
        this.children.add(child);
        child.setParent(this);
    }

    public void removeChild(CategoriesEntity child) {
        this.children.remove(child);
        child.setParent(null);
    }

    protected void setParent(CategoriesEntity parent) {
        this.parent = parent;
    }
}