package com.example.anyword.repository;

import com.example.anyword.entity.ArticleImageEntity;
import java.util.List;

public interface ArticleImageRepository {
  ArticleImageEntity save(ArticleImageEntity entity);
  List<String> findByArticleId(Long articleId);
  int deleteByArticleId(Long articleId);
}