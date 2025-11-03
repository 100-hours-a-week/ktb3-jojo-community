package com.example.anyword.repository.comment;

import com.example.anyword.entity.CommentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
  long countByArticleId(Long articleId);
  List<CommentEntity> findAllByArticleIdOrderByCreatedAtDesc(Long articleId);
}