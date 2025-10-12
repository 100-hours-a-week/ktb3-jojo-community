package com.example.anyword.repository;

import com.example.anyword.entity.LikeArticleEntity;
import com.example.anyword.shared.constants.ResponseMessage;
import com.example.anyword.shared.exception.ConflictException;
import org.springframework.stereotype.Repository;

@Repository
public class LikeArticleRepository extends BaseRepository<LikeArticleEntity>{
  public LikeArticleRepository(){
    super();
  }

  public int countByArticleId(Long articleId) {
    return (int) store.values().stream()
        .filter(e -> articleId.equals(e.getArticleId()))
        .count();
  }

  public boolean existsByArticleIdAndUserId(Long articleId, Long currentUserId) {
    return store.values().stream()
        .anyMatch(e -> articleId.equals(e.getArticleId())
            && currentUserId.equals(e.getUserId()));
  }



  @Override
  public LikeArticleEntity save(LikeArticleEntity e) {
    if (existsByArticleIdAndUserId(e.getArticleId(), e.getUserId())) {
      throw new ConflictException(ResponseMessage.LIKED_CONFLICT);
    }
    return super.save(e);
  }

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
