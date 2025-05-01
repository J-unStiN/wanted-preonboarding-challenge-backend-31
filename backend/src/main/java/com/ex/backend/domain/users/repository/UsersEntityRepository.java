package com.ex.backend.domain.users.repository;

import com.ex.backend.domain.users.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersEntityRepository extends JpaRepository<UsersEntity, Long> {
}
