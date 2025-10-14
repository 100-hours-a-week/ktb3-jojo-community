package com.example.anyword.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ArticleEntity implements BaseEntity<Long> {
  private Long id; //PK - 초기에 null ..?

  private Long userId; //FK

  private String title;
  private String contents;
  private long viewCnt;

  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime createdAt;

  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime updatedAt;

  public ArticleEntity(){};

  public ArticleEntity(Long userId, String title, String contents){
    this.userId = userId;
    this.title = title;
    this.contents = contents;
    this.viewCnt = 0;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  private ArticleEntity(Long id, Long userId, String title, String contents,
      long viewCnt, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.userId = userId;
    this.title = title;
    this.contents = contents;
    this.viewCnt = viewCnt;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static ArticleEntity copyWith(ArticleEntity original, String newTitle, String newContent) {
    return new ArticleEntity(
        original.getId(),
        original.getUserId(),
        newTitle != null ? newTitle : original.getTitle(),
        newContent != null ? newContent : original.getContents(),
        original.getViewCnt(),
        original.getCreatedAt(),
        LocalDateTime.now()
    );
  }

  public void incrementViews() {
    this.viewCnt++;
  }

  @Override
  public Long getId() {
    return id;
  }

  public Long getUserId() {
    return userId;
  }

  public long getViewCnt() {
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

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }


  @Override
  public void setId(Long articleId) {
    this.id = articleId;
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

  public void setUpdatedAt(LocalDateTime time){this.updatedAt = time;}
}
