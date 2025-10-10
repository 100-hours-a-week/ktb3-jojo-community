package com.example.anyword.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class LikeArticleEntity implements BaseEntity<Long> {
  private Long id;
  private final Long articleId;
  private final Long userId;
  @JsonFormat(timezone = "Asia/Seoul")
  private final LocalDateTime createdAt;

  public LikeArticleEntity(Long articleId, Long userId, LocalDateTime createdAt) {
    this.articleId = articleId;
    this.userId = userId;
    this.createdAt = createdAt;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Long getArticleId() {
    return articleId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Long getUserId() {
    return userId;
  }

}
