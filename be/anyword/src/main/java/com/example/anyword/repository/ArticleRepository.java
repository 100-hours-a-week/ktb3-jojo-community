package com.example.anyword.repository;

import com.example.anyword.dto.ArticleDTO;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository extends BaseRepository<ArticleDTO> {

  public ArticleRepository(){
    super();
  }



}
