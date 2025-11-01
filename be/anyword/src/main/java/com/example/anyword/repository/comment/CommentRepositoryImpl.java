package com.example.anyword.repository.comment;

import com.example.anyword.entity.CommentEntity;
import com.example.anyword.repository.BaseRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositoryImpl extends BaseRepository<CommentEntity> implements CommentRepository{
  public CommentRepositoryImpl(){
    super();
  }

  @Override
  public long countByArticleId(Long articleId) {
    return store.values()
        .stream()
        .filter(image -> image.getArticle().getId().equals(articleId))
        .count();
  }

  @Override
  public List<CommentEntity> findAllByArticleIdOrderByCreatedAtDesc(Long articleId) {
    return store.values()
        .stream()
        .filter(comment -> comment.getArticle().getId().equals(articleId))
        .sorted(Comparator.comparing(CommentEntity::getCreatedAt).reversed())
        .collect(Collectors.toList());
  }
}

