package com.example.anyword.entity;

import static com.example.anyword.shared.constants.ValidErrorMessage.NO_TITLE_OR_CONTENTS;

import com.example.anyword.shared.exception.BadRequestException;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long id; //PK

  @ManyToOne
  @JoinColumn(name="article_id", nullable = false)
  private ArticleEntity article; //FK

  @ManyToOne
  @JoinColumn(name="user_id", nullable = false)
  private UserEntity author; //FK2

  @Column(nullable = false)
  private String contents;

  @Column(nullable = false)
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime createdAt;

  @Column(nullable = false)
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

  private void updateContent(String newContent){
    if (newContent == null || newContent.isEmpty()){
      System.out.println("merge 와 함께 사용해주세요."); // 개발 로그
      throw new BadRequestException(NO_TITLE_OR_CONTENTS);
    }
    this.contents = newContent;
    this.updatedAt = LocalDateTime.now();
  }

  // setter 사용 -> 의미 명확한 메서드로 변경
  public CommentEntity returnUpdatedComment(String newContent) {
    this.updateContent(newContent);
    return this;
  }

}
