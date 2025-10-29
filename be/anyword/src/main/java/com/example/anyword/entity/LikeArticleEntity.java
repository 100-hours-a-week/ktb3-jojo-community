package com.example.anyword.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
public class LikeArticleEntity implements BaseEntity<Long> {
  @Setter
  @Id
  @GeneratedValue
  private Long id;

  private final Long articleId;

  private final Long userId;

  @JsonFormat(timezone = "Asia/Seoul")
  private final LocalDateTime createdAt;

  public LikeArticleEntity(Long articleId, Long userId) {
    this.articleId = articleId;
    this.userId = userId;
    this.createdAt = LocalDateTime.now();
  }

}
