package com.example.anyword.repository;

import com.example.anyword.dto.CommentDTO;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository extends BaseRepository<CommentDTO>{
  public CommentRepository(){
    super();
  }

}
