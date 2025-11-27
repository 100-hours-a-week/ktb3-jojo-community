package com.example.anyword.dto.comment;

import lombok.Getter;

@Getter
public class CreateCommentResponseDto {
  private final Long commentId;
  private final Long articleId;


  public CreateCommentResponseDto(Long commentId, Long articleId) {
    this.commentId = commentId;
    this.articleId = articleId;
  }

}
