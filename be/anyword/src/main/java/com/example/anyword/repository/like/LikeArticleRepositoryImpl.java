package com.example.anyword.repository.like;

import com.example.anyword.entity.LikeArticleEntity;
import com.example.anyword.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LikeArticleRepositoryImpl extends BaseRepository<LikeArticleEntity> implements LikeArticleRepository{
  public LikeArticleRepositoryImpl(){
    super();
  }

  @Override
  public int countByArticleId(Long articleId) {
    return (int) store.values().stream()
        .filter(e -> articleId.equals(e.getArticleId()))
        .count();
  }

  @Override
  public boolean existsByArticleIdAndUserId(Long articleId, Long currentUserId) {
    return store.values().stream()
        .anyMatch(e -> articleId.equals(e.getArticleId())
            && currentUserId.equals(e.getUserId()));
  }

  @Override
  public boolean deleteByArticleIdAndUserId(Long articleId, Long userId) {
    Long keyToRemove = null;

    for (var entry : store.entrySet()) {
      LikeArticleEntity e = entry.getValue();
      if (articleId.equals(e.getArticleId()) && userId.equals(e.getUserId())) {
        keyToRemove = entry.getKey();
        break;
      }
    }
    if (keyToRemove != null) {
      store.remove(keyToRemove);
      return true;
    }
    return false;
  }

}
