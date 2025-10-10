package com.example.anyword.repository;

import com.example.anyword.entity.ArticleEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository extends BaseRepository<ArticleEntity> {

  public ArticleRepository(){
    super();
  }



}
