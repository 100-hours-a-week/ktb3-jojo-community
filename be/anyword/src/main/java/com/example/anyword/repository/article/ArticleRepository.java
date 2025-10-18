package com.example.anyword.repository.article;

import com.example.anyword.entity.ArticleEntity;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
  ArticleEntity save(ArticleEntity entity);
  Optional<ArticleEntity> findById(Long id);
  List<ArticleEntity> findAll();
  boolean deleteById(Long id);

  /** 최신순 조회 */
  List<ArticleEntity> findAllByOrderByCreatedAtDesc(int page, int size);

  /** 조회순 조회 */
  List<ArticleEntity> findAllOrderByPopularity(int page, int size);

  /** 전체 게시글 개수 */
  long count();
}
