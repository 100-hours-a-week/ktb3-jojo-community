package com.example.anyword.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class CommentEntity implements BaseEntity<Long> {

  private Long id; //PK

  private final Long articleId; //FK
  private final Long userId; //FK

  private String contents;
  @JsonFormat(timezone = "Asia/Seoul")
  private final LocalDateTime createdAt;
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime updatedAt;

  public CommentEntity(Long articleId, Long userId, String contents) {
    this.articleId = articleId;
    this.userId = userId;
    this.contents = contents;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }




  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getArticleId() {
    return articleId;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
