package com.example.anyword.Auth.repository;

import com.example.anyword.Auth.entity.RefreshTokenEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
  Optional<RefreshTokenEntity> findByTokenHash(String tokenHash);
  Optional<RefreshTokenEntity> findByUserId(Long userId);
}
