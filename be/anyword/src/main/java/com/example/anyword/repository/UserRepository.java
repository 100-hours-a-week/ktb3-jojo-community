package com.example.anyword.repository;

import com.example.anyword.entity.UserEntity;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BaseRepository<UserEntity> {

  public UserRepository(){
    super();
  }

  public Optional<UserEntity> findByEmail(String email) {
    return findAll().stream()
        .filter(user -> user.getEmail().equals(email))
        .findFirst();
  }
}
