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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleImageEntity implements BaseEntity<Long> {
  @Id @Setter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long id; //PK

  @ManyToOne
  @JoinColumn(name="article_id", nullable = false)
  private ArticleEntity article;

  @Column(nullable = false) @Setter
  private String imageURL;

  @Column(nullable = false) @Setter
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime createdAt;

  public ArticleImageEntity(ArticleEntity article, String imageURL){
    this.article = article;
    this.imageURL = imageURL;
    this.createdAt = LocalDateTime.now();
  }

}
