package com.example.anyword.Comment.repository;

import com.example.anyword.Comment.entity.CommentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
  @Query("select count(c) from CommentEntity c where c.article.id in (:article_id)")
  long countByArticleId(@Param("article_id") Long articleId);
  @Query("""
  select c.article.id, count(c)
  from CommentEntity c
  where c.article.id in :articleIds
  group by c.article.id
""")
  List<Object[]> bulkCountByArticleId(@Param("articleIds") List<Long> articleIds);
  List<CommentEntity> findAllByArticleIdOrderByCreatedAtAsc(Long articleId);
}