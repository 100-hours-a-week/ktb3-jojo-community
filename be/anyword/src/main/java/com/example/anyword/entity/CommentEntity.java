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
public class CommentEntity implements BaseEntity<Long> {
  @Setter
  @Id
  @GeneratedValue
  private Long id; //PK

  private final Long articleId; //FK

  private final Long userId; //FK

  @Setter
  private String contents;

  @JsonFormat(timezone = "Asia/Seoul")
  private final LocalDateTime createdAt;

  @Setter
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime updatedAt;

  public CommentEntity(Long articleId, Long userId, String contents) {
    this.articleId = articleId;
    this.userId = userId;
    this.contents = contents;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  public CommentEntity(Long articleId, Long userId, String contents, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.articleId = articleId;
    this.userId = userId;
    this.contents = contents;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static CommentEntity copyWith(CommentEntity original, String newContent){
    return new CommentEntity(
        original.getId(),
        original.getUserId(),
        newContent,
        original.getCreatedAt(),
        LocalDateTime.now()
    );
  }

}
