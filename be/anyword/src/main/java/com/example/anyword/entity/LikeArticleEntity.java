package com.example.anyword.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeArticleEntity implements BaseEntity<Long> {
  @Id @Setter
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_article_sequence")
  @Column(unique = true, nullable = false)
  private Long id;

  @Column(nullable = false)
  private Long articleId;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime createdAt;

  public LikeArticleEntity(Long articleId, Long userId) {
    this.articleId = articleId;
    this.userId = userId;
    this.createdAt = LocalDateTime.now();
  }

}
