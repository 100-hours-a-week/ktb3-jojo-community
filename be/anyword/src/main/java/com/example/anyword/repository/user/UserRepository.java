package com.example.anyword.repository.user;

import com.example.anyword.entity.UserEntity;
import java.util.Optional;

public interface UserRepository {
  Optional<UserEntity> findByEmail(String email);
  Optional<UserEntity> findById(Long id);
  UserEntity nonOptionalFindById(Long id);
  Optional<UserEntity> findByNickname(String nickname);
  boolean isEmailExist(String email);
  boolean isNicknameExist(String nickname);
  UserEntity update(UserEntity user);
  UserEntity save(UserEntity user);
  boolean deleteById(Long id);
}
