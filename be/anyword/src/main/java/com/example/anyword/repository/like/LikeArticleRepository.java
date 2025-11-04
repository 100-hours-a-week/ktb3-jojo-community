package com.example.anyword.repository.like;

import com.example.anyword.entity.ArticleEntity;
import com.example.anyword.entity.LikeArticleEntity;
import com.example.anyword.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LikeArticleRepository extends JpaRepository<LikeArticleEntity, Long> {
  @Query("select count(l) from LikeArticleEntity l where l.article.id in (:article_id)")
  int countByArticleId(@Param("article_id") Long articleId);
  boolean existsByArticleAndAuthor(ArticleEntity article, UserEntity author);
  boolean deleteByArticleAndAuthor(ArticleEntity article, UserEntity author);
}