package com.example.anyword.Article.repository;

import com.example.anyword.Article.entity.ArticleEntity;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
  /** 최신순 조회 */
  @Query("select a from ArticleEntity a join fetch a.author order by a.createdAt desc")
  List<ArticleEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

  /** 조회순 조회 */
  @Query("select a from ArticleEntity a join fetch a.author order by a.viewCnt desc")
  List<ArticleEntity> findAllByOrderByViewCntDesc(Pageable pageable);

  /** 특정 유저의 모든 게시글 ID 조회 */
  @Query("select a.id from ArticleEntity a where a.author.id = :authorId")
  List<Long> findAllIdsByAuthorId(@Param("authorId") Long authorId);

  /** 특정 유저의 모든 게시글 삭제 */
  @Modifying
  @Query("delete from ArticleEntity a where a.author.id = :authorId")
  void deleteAllByAuthorId(@Param("authorId") Long authorId);
}
