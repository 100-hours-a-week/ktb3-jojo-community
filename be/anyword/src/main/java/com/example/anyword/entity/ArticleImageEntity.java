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
public class ArticleImageEntity implements BaseEntity<Long> {
  @Setter
  @Id
  @GeneratedValue
  private Long id; //PK

  private final Long articleId; //FK

  @Setter
  private String imageURL;

  @Setter
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime createdAt;

  public ArticleImageEntity(Long articleId, String imageURL){
    this.articleId = articleId;
    this.imageURL = imageURL;
    this.createdAt = LocalDateTime.now();
  }

}
