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
public class CommentEntity implements BaseEntity<Long> {
  @Id @Setter
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
  @Column(unique = true, nullable = false)
  private Long id; //PK

  @Column(nullable = false)
  private Long articleId; //FK

  @Column(nullable = false)
  private Long userId; //FK

  @Column(nullable = false) @Setter
  private String contents;

  @Column(nullable = false)
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime createdAt;

  @Column(nullable = false) @Setter
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
