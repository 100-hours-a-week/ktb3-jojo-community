package com.example.anyword.repository.user;

import com.example.anyword.entity.UserEntity;
import com.example.anyword.repository.BaseRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepositoryImpl extends BaseRepository<UserEntity> implements UserRepository{

  public UserRepositoryImpl(){
    super();
  }

  @Override
  public Optional<UserEntity> findByEmail(String email) {
    return findAll().stream()
        .filter(user -> user.getEmail().equals(email))
        .findFirst();
  }

  @Override
  public UserEntity nonOptionalFindById(Long id) {
    return this.store.get(id);
  }

  @Override
  public boolean isEmailExist(String email){
    return this.findByEmail(email).isPresent();
  }

  @Override
  public Optional<UserEntity> findByNickname(String nickname){
    return findAll().stream()
        .filter(user -> user.getEmail().equals(nickname))
        .findFirst();
  }

  @Override
  public boolean isNicknameExist(String nickname){
    return this.findByNickname(nickname).isPresent();
  }

  @Override
  public UserEntity update(UserEntity user) {
    return null;
  }

}
