package com.example.anyword.Article.dto.response;

import lombok.Getter;

@Getter
public class PostArticleResponseDto {
  private final Long articleId;

  public PostArticleResponseDto(Long articleId) {
    this.articleId = articleId;
  }

}
