package com.example.anyword.repository.comment;

import com.example.anyword.entity.CommentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
  @Query("select count(c) from CommentEntity c where c.article.id in (:article_id)")
  long countByArticleId(@Param("article_id") Long articleId);
  @Query("select count(c) from CommentEntity c where c.article.id in (:article_id) group by c.article.id")
  List<Object[]> bulkCountByArticleId(@Param("article_id") List<Long> articleId);
  List<CommentEntity> findAllByArticleIdOrderByCreatedAtDesc(Long articleId);
}