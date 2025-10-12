package com.example.anyword.dto.article;

public class PostArticleResponseDto {
  private final Long postId;

  public PostArticleResponseDto(Long postId) {
    this.postId = postId;
  }

  public Long getPostId() {
    return postId;
  }
}
