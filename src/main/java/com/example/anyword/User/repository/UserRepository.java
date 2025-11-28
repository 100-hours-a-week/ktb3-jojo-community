package com.example.anyword.User.repository;

import com.example.anyword.User.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String email);
  Optional<UserEntity> findByNickname(String nickname);
  boolean existsByEmail(String email);
  boolean existsByNickname(String nickname);
}
