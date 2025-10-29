package com.example.anyword.dto.article;

import com.example.anyword.entity.ArticleEntity;
import java.time.LocalDateTime;
import java.util.List;

public class ArticleListItemDto {

  private Long articleId;
  private String title;
  private List<String> imageUrls;
  private AuthorInfoDto author;
  private ArticleStatusInfoDto status;
  private LocalDateTime createdAt;

  public ArticleListItemDto() {}

  public static ArticleListItemDto from(ArticleEntity article, AuthorInfoDto author,
      ArticleStatusInfoDto status){
    ArticleListItemDto dto = new ArticleListItemDto();
    dto.articleId = article.getId();
    dto.title = article.getTitle();
    dto.author = author;
    dto.status = status;
    dto.createdAt = article.getCreatedAt();
    return dto;
  }


  public Long getArticleId() {
    return articleId;
  }

  public String getTitle() {
    return title;
  }


  public List<String> getImageUrls() {
    return imageUrls;
  }


  public AuthorInfoDto getAuthor() {
    return author;
  }

  public ArticleStatusInfoDto getStatus() {
    return status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}