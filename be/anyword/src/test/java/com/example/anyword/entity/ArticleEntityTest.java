package com.example.anyword.entity;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
class ArticleEntityTest {

  @PersistenceContext
  EntityManager entityManager;

  @Test
  @Rollback
  void unidirectionalManyToOneTest() {
    // 유저 저장
    UserEntity user = new UserEntity("tester@gmail.com", "123aS!", "tester", "https://sdnf");
    entityManager.persist(user);
    entityManager.flush();

    // 게시글 저장
    ArticleEntity article = new ArticleEntity(user, "공지 글", "내용");
    entityManager.persist(article);
    entityManager.flush();


    ArticleEntity findPost = entityManager.find(ArticleEntity.class, article.getId());
    System.out.println("findPost.getId() : " + findPost.getId());
    System.out.println("findPost.getTitle() : " + findPost.getTitle());
    System.out.println("findPost.getauthor().getNickname() : " + findPost.getAuthor().getNickname());
  }

}