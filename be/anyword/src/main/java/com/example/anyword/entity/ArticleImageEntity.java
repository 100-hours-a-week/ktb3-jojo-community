package com.example.anyword.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ArticleImageEntity implements BaseEntity<Long> {
  private Long id; //PK

  private final Long articleId; //FK
  private String imageURL;

  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime createdAt;

  public ArticleImageEntity(Long articleId, String imageURL, LocalDateTime createdAt){
    this.articleId = articleId;
    this.imageURL = imageURL;
    this.createdAt = createdAt;
  }


  @Override
  public Long getId(){
    return id;
  }


  public Long getArticleId() {
    return articleId;
  }


  public String getImageURL(){
    return imageURL;
  }

  public LocalDateTime getCreatedAt(){
    return createdAt;
  }


  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

}
