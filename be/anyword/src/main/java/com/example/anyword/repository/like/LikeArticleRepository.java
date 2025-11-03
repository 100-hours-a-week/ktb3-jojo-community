package com.example.anyword.repository.like;

import com.example.anyword.entity.ArticleEntity;
import com.example.anyword.entity.LikeArticleEntity;
import com.example.anyword.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikeArticleRepository extends JpaRepository<LikeArticleEntity, Long> {
  int countByArticleId(Long articleId);
  boolean existsByArticleAndAuthor(ArticleEntity article, UserEntity author);
  boolean deleteByArticleAndAuthor(ArticleEntity article, UserEntity author);
}