package com.example.anyword.repository.article;

import com.example.anyword.entity.ArticleEntity;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
  /** 최신순 조회 */
  @Query("select a from ArticleEntity a join fetch a.author order by a.createdAt desc")
  List<ArticleEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

  /** 조회순 조회 */
  @Query("select a from ArticleEntity a join fetch a.author order by a.viewCnt desc")
  List<ArticleEntity> findAllByOrderByViewCntDesc(Pageable pageable);
}
