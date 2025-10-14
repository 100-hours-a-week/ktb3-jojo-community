package com.example.anyword.repository;

import com.example.anyword.entity.CommentEntity;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository extends BaseRepository<CommentEntity>{
  public CommentRepository(){
    super();
  }

  public long countByArticleId(Long articleId) {
    return store.values()
        .stream()
        .filter(image -> image.getArticleId().equals(articleId))
        .count();
  }

  public List<CommentEntity> findAllByArticleIdOrderByCreatedAtDesc(Long articleId) {
    return store.values()
        .stream()
        .filter(comment -> comment.getArticleId().equals(articleId))
        .sorted(Comparator.comparing(CommentEntity::getCreatedAt).reversed())
        .collect(Collectors.toList());
  }
}
