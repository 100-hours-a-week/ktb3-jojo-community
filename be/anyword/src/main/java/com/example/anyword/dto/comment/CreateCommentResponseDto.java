package com.example.anyword.dto.comment;

public class CreateCommentResponseDto {
  private final Long commentId;
  private final Long articleId;


  public CreateCommentResponseDto(Long commentId, Long articleId) {
    this.commentId = commentId;
    this.articleId = articleId;
  }

  public Long getCommentId() {
    return commentId;
  }

  public Long getArticleId() {
    return articleId;
  }
}
