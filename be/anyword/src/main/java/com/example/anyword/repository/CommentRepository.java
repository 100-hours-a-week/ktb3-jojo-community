package com.example.anyword.repository;

import com.example.anyword.entity.CommentEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository extends BaseRepository<CommentEntity>{
  public CommentRepository(){
    super();
  }

}
