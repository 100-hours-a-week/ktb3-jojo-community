package com.example.anyword.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentEntity implements BaseEntity<Long> {
  @Id @Setter
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
  @Column(unique = true, nullable = false)
  private Long id; //PK

  @ManyToOne
  @JoinColumn(name="article_id", nullable = false) @Setter
  private ArticleEntity article; //FK

  @ManyToOne
  @JoinColumn(name="user_id", nullable = false) @Setter
  private UserEntity author; //FK2

  @Column(nullable = false) @Setter
  private String contents;

  @Column(nullable = false)
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime createdAt;

  @Column(nullable = false) @Setter
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime updatedAt;



  public CommentEntity(ArticleEntity article, UserEntity author, String contents) {
    this.article = article;
    this.author = author;
    this.contents = contents;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  public CommentEntity(ArticleEntity article, UserEntity author, String contents, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.article = article;
    this.author = author;
    this.contents = contents;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static CommentEntity copyWith(CommentEntity original, String newContent){
    return new CommentEntity(
        original.getId(),
        original.getArticle(),
        original.getAuthor(),
        newContent,
        original.getCreatedAt(),
        LocalDateTime.now()
    );
  }

}
