package com.ex.backend.domain.users.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    public UsersEntity(String name, String email, String avatarUrl) {
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String name, String email, String avatarUrl) {
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }
}