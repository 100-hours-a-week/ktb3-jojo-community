package com.example.anyword.repository.comment;

import com.example.anyword.entity.CommentEntity;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {
  CommentEntity save(CommentEntity entity);
  Optional<CommentEntity> findById(Long commentId);
  boolean deleteById(Long id);
  List<CommentEntity> findAll();
  long countByArticleId(Long articleId);
  List<CommentEntity> findAllByArticleIdOrderByCreatedAtDesc(Long articleId);
}