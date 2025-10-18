package com.example.anyword.repository;

import com.example.anyword.entity.LikeArticleEntity;
import java.util.List;
import java.util.Optional;


public interface LikeArticleRepository {
  LikeArticleEntity save(LikeArticleEntity entity);
  Optional<LikeArticleEntity> findById(Long id);
  List<LikeArticleEntity> findAll();
  boolean deleteById(Long id);

  int countByArticleId(Long articleId);
  boolean existsByArticleIdAndUserId(Long articleId, Long userId);
  boolean deleteByArticleIdAndUserId(Long articleId, Long userId);
}