package com.example.anyword.repository.article;

import com.example.anyword.entity.ArticleEntity;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
  /** 최신순 조회 */
  List<ArticleEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

  /** 조회순 조회 */
  List<ArticleEntity> findAlByOrderByViewCntDesc(Pageable pageable);
}
