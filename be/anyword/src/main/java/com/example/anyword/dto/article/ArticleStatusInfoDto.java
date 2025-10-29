package com.example.anyword.dto.article;

public class ArticleStatusInfoDto {

  private long likes;
  private long comments;
  private long views;

  public ArticleStatusInfoDto(){};

  public ArticleStatusInfoDto(long likes, long comments, long views) {
    this.likes = likes;
    this.comments = comments;
    this.views = views;
  }

  public long getLikes() {
    return likes;
  }

  public void setLikes(long likes) {
    this.likes = likes;
  }

  public long getComments() {
    return comments;
  }

  public void setComments(long comments) {
    this.comments = comments;
  }

  public long getViews() {
    return views;
  }

  public void setViews(long views) {
    this.views = views;
  }
}