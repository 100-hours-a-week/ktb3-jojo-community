package com.example.anyword.repository.articleImage;

import com.example.anyword.entity.ArticleImageEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleImageRepository extends JpaRepository<ArticleImageEntity, Long> {
  List<String> findByArticleId(Long articleId);
  void deleteByArticleId(Long articleId);
}