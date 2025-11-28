package com.example.anyword.Article.repository;

import com.example.anyword.Article.entity.ArticleImageEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface ArticleImageRepository extends JpaRepository<ArticleImageEntity, Long> {
  List<ArticleImageEntity> findByArticleId(Long articleId);

  @Modifying
  void deleteByArticleId(Long articleId);
}