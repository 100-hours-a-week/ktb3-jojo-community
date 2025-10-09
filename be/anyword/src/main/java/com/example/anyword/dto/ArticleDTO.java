package com.example.anyword.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ArticleDTO implements BaseDTO<Long> {
  private Long id; //PK - 초기에 null ..?

  private final Long userId; //FK

  private String title;
  private String contents;
  private Long viewCnt;

  @JsonFormat(timezone = "Asia/Seoul")
  private final LocalDateTime createdAt;

  public ArticleDTO(Long userId, String title, String contents, LocalDateTime createdAt){
    this.userId = userId;
    this.title = title;
    this.contents = contents;
    this.viewCnt = 0L;
    this.createdAt = createdAt;
  }

  @Override
  public Long getId() {
    return id;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getViewCnt() {
    return viewCnt;
  }

  public String getContents() {
    return contents;
  }

  public String getTitle() {
    return title;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public void setId(Long postId) {
    this.id = postId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public void setViewCnt(Long viewCnt) {
    this.viewCnt = viewCnt;
  }
}
