package com.example.anyword.dto.article;

import com.example.anyword.entity.ArticleEntity;
import java.time.LocalDateTime;
import java.util.List;

public class ArticleListItem {

  private Long articleId;
  private String title;
  private List<String> imageUrls;
  private AuthorInfo author;
  private ArticleStatusInfo status;
  private LocalDateTime createdAt;

  public ArticleListItem() {}

  public static ArticleListItem from(ArticleEntity article, AuthorInfo author,
      ArticleStatusInfo status){
    ArticleListItem dto = new ArticleListItem();
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


  public AuthorInfo getAuthor() {
    return author;
  }

  public ArticleStatusInfo getStatus() {
    return status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}