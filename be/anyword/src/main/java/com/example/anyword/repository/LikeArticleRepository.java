package com.example.anyword.repository;

import com.example.anyword.dto.LikeArticleDTO;
import org.springframework.stereotype.Repository;

@Repository
public class LikeArticleRepository extends BaseRepository<LikeArticleDTO>{
  public LikeArticleRepository(){
    super();
  }
}
