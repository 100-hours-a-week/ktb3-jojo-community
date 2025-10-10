package com.example.anyword.repository;

import com.example.anyword.entity.LikeArticleEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LikeArticleRepository extends BaseRepository<LikeArticleEntity>{
  public LikeArticleRepository(){
    super();
  }
}
