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

  @Test
  @Rollback
  void bidirectionalOnetoManyTest() {
    // 유저 생성 및 저장
    UserEntity user = new UserEntity("tester@gmail.com", "123aS!", "tester", "https://sdnf");
    entityManager.persist(user);
    // flush 시 INSERT 발생

    // 게시글 3개 생성
    ArticleEntity noticePost = new ArticleEntity(user, "공지사항", "공지 내용");
    ArticleEntity freePost1 = new ArticleEntity(user, "자유게시판 글1", "자유 내용");
    ArticleEntity freePost2 = new ArticleEntity(user, "자유게시판 글2", "자유 내용");

    // 연관관계 설정 (편의 메서드 사용)
    user.addArticle(noticePost);
    user.addArticle(freePost1);
    user.addArticle(freePost2);

    // 게시글 저장
    entityManager.persist(noticePost);
    entityManager.persist(freePost1);
    entityManager.persist(freePost2);

    // flush 로 DB 반영, clear 로 영속성 컨텍스트 초기화
    entityManager.flush();
    entityManager.clear();

    UserEntity findUser = entityManager.find(UserEntity.class, user.getId());
    System.out.println("조회된 유저 닉네임 = " + findUser.getNickname());
    System.out.println("연관된 게시글 수 = " + findUser.getArticles().size());
    findUser.getArticles().forEach(post ->
        System.out.println("post.id = " + post.getId() + ", title = " + post.getTitle())
    );

    ArticleEntity findPost = entityManager.find(ArticleEntity.class, freePost1.getId());
    System.out.println("조회된 게시글 제목 = " + findPost.getTitle());
    System.out.println("작성자 닉네임 = " + findPost.getAuthor().getNickname());
  }

}