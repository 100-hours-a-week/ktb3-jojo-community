package com.example.anyword.dto.article;

import com.example.anyword.entity.ArticleEntity;
import java.time.LocalDateTime;
import java.util.List;

public class GetArticleResponseDto {
  private Long postId;
  private String title;
  private String contents;
  private AuthorInfo author;
  private ArticleStatusInfo status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private List<String> imageUrls;
  private Boolean likedByMe;
  private Boolean isMyContents;


  public static GetArticleResponseDto from(
      ArticleEntity article,
      AuthorInfo author,
      ArticleStatusInfo status,
      boolean likedByMe,
      boolean isMyContents,
      List<String> imageUrls
  ) {
    GetArticleResponseDto dto = new GetArticleResponseDto();
    dto.postId = article.getId();
    dto.title = article.getTitle();
    dto.contents = article.getContents();
    dto.author = author;
    dto.status = status;
    dto.createdAt = article.getCreatedAt();
    dto.updatedAt = article.getUpdatedAt();
    dto.imageUrls = imageUrls;
    dto.likedByMe = likedByMe;
    dto.isMyContents = isMyContents;
    return dto;
  }

  public Long getPostId() {
    return postId;
  }


  public String getTitle() {
    return title;
  }


  public String getContents() {
    return contents;
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



  public Boolean getLikedByMe() {
    return likedByMe;
  }



  public Boolean getIsMyContents() {
    return isMyContents;
  }



  public List<String> getImageUrls() {
    return imageUrls;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
