package com.example.anyword.repository;

import com.example.anyword.entity.UserEntity;
import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepositoryImpl extends BaseRepository<UserEntity> implements UserRepository {

  public UserRepositoryImpl(){
    super();
  }

  public Optional<UserEntity> findByEmail(String email) {
    return findAll().stream()
        .filter(user -> user.getEmail().equals(email))
        .findFirst();
  }

  public boolean isEmailExist(String email){
    return this.findByEmail(email).isPresent();
  }

  private Optional<UserEntity> findByNickname(String nickname){
    return findAll().stream()
        .filter(user -> user.getEmail().equals(nickname))
        .findFirst();
  }

  public boolean isNicknameExist(String nickname){
    return this.findByNickname(nickname).isPresent();
  }

}
