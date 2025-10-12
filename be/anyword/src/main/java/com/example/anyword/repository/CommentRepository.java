package com.example.anyword.repository;

import com.example.anyword.entity.CommentEntity;
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
}
