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

  public Boolean isEmailExist(String email){
    return this.findByEmail(email).isPresent();
  }

  private Optional<UserEntity> findByNickname(String nickname){
    return findAll().stream()
        .filter(user -> user.getEmail().equals(nickname))
        .findFirst();
  }

  public Boolean isNicknameExist(String nickname){
    return this.findByNickname(nickname).isPresent();
  }


}
