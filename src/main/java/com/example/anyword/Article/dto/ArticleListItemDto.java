package com.example.anyword.Article.dto;

import com.example.anyword.Article.entity.ArticleEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
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


}