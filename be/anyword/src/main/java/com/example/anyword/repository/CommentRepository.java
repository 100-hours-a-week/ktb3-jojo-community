package com.example.anyword.repository;

import com.example.anyword.entity.CommentEntity;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

public interface CommentRepository {
  CommentEntity save(CommentEntity entity);
  Optional<CommentEntity> findById(Long commentId);
  boolean deleteById(Long id);
  List<CommentEntity> findAll();
  long countByArticleId(Long articleId);
  List<CommentEntity> findAllByArticleIdOrderByCreatedAtDesc(Long articleId);
}