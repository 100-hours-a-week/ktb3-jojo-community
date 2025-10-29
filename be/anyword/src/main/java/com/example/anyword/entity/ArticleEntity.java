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
public class ArticleEntity implements BaseEntity<Long> {
  @Id @Setter @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_sequence")
  @Column(unique = true, nullable = false)
  private Long id; //PK

  @Column(nullable = false)
  private Long userId; //FK

  @Column(nullable = false) @Setter
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT") @Setter
  private String contents;

  @Column(nullable = false) @Setter
  private long viewCnt;

  @Column(nullable = false)
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime createdAt;

  @Column(nullable = false) @Setter
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime updatedAt;

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

}
