package com.example.anyword.repository;

import com.example.anyword.dto.ArticleImageDTO;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleImageRepository extends BaseRepository<ArticleImageDTO>{
  public ArticleImageRepository(){
    super();
  }
}
