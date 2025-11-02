package com.example.anyword.repository.user;

import com.example.anyword.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class UserRepositoryJpql implements UserRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Optional<UserEntity> findByEmail(String email) {
    String jpql = "select u from UserEntity u where u.email = :email";
    TypedQuery<UserEntity> query = em.createQuery(jpql, UserEntity.class);
    query.setParameter("email", email);
    List<UserEntity> result = query.getResultList();

    return result.isEmpty() ? Optional.empty() : result.stream().findFirst();
  }

  @Override
  public Optional<UserEntity> findById(Long id) {
    UserEntity user = em.find(UserEntity.class, id);
    return Optional.ofNullable(user);
  }

  @Override
  public UserEntity nonOptionalFindById(Long id) {
    return em.find(UserEntity.class, id);
  }

  @Override
  public Optional<UserEntity> findByNickname(String nickname) {
    String jpql = "select u from UserEntity u where u.nickname = :nickname";
    TypedQuery<UserEntity> query = em.createQuery(jpql, UserEntity.class);
    query.setParameter("nickname", nickname);
    List<UserEntity> result = query.getResultList();

    return result.isEmpty() ? Optional.empty() : result.stream().findFirst();
  }

  @Override
  public boolean isEmailExist(String email) {
    return this.findByEmail(email).isPresent();
  }

  @Override
  public boolean isNicknameExist(String nickname) {
    return this.findByNickname(nickname).isPresent();
  }

  @Override
  public UserEntity save(UserEntity user) {
    em.persist(user);
    return user;
  }

  public UserEntity update(UserEntity user){
    UserEntity foundUser = em.find(UserEntity.class, user.getId());


    foundUser.setNickname(user.getNickname());
    foundUser.setProfileImageUrl(user.getProfileImageUrl());
    foundUser.setPassword(user.getPassword());
    return foundUser;
  }


  @Override
  public boolean deleteById(Long id) {
    String jpql = "delete from UserEntity u where u.id = :id";
    TypedQuery<UserEntity> query = em.createQuery(jpql, UserEntity.class);
    query.setParameter("id", id);
    return false;
  }
}
