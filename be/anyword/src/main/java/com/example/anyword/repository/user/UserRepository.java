package com.example.anyword.repository.user;

import com.example.anyword.entity.UserEntity;
import java.util.Optional;

public interface UserRepository {
  Optional<UserEntity> findByEmail(String email);
  Optional<UserEntity> findById(Long id);
  UserEntity nonOptionalFindById(Long id);
  boolean isEmailExist(String email);
  boolean isNicknameExist(String nickname);
  UserEntity save(UserEntity user);
  Optional<UserEntity> findByNickname(String nickname);
  boolean deleteById(Long id);
}
