package com.example.anyword.Article.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleStatusInfoDto {

  private long likes;
  private long comments;
  private long views;

  public ArticleStatusInfoDto(){}

  public ArticleStatusInfoDto(long likes, long comments, long views) {
    this.likes = likes;
    this.comments = comments;
    this.views = views;
  }
}