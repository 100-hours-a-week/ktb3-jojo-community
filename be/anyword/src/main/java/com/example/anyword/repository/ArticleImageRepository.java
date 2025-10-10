package com.example.anyword.repository;

import com.example.anyword.entity.ArticleImageEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleImageRepository extends BaseRepository<ArticleImageEntity>{
  public ArticleImageRepository(){
    super();
  }
}
