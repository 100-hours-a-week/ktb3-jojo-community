package com.example.anyword.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserEntityTest {

  @PersistenceContext
  EntityManager em;

  @Test
  @Rollback()
  void idTest(){
    UserEntity user = new UserEntity("suspen064@gmail.com", "123456789!", "jojo", "https://sss");

    em.persist(user);

  }

  @Test
  @Rollback()
  void idStrategyTest(){
    // 5개의 더미데이터 추가
    for (int i = 1; i <= 5; i++) {
      UserEntity user = new UserEntity(
          "tester" + i + "@gmail.com",
          "1231224sf!" + i,
          "jieun" + i,
          "https://sss"

      );
      em.persist(user);
    }
  }




}