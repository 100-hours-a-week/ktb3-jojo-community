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
  @Rollback(false)
  void idTest(){
    UserEntity user = new UserEntity("suspen064@gmail.com", "123456789!", "jojo", "https://sss");

    em.persist(user);

  }

}