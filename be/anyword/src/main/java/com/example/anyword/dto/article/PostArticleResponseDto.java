package com.example.anyword.dto.article;

public class PostArticleResponseDto {
  private final Long articleId;

  public PostArticleResponseDto(Long articleId) {
    this.articleId = articleId;
  }

  public Long getArticleId() {
    return articleId;
  }
}
