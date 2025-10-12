package com.example.anyword.dto.article.request;

import com.example.anyword.entity.ArticleEntity;
import jakarta.validation.constraints.Size;
import java.util.List;

public class PutArticleRequestDto {
  @Size(max = 26)
  private String title;

  private String contents;

  //nullable
  private List<String> imageUrls;

  public PutArticleRequestDto() {}


  public String getTitle() {
    return title;
  }

  public String getContents() {
    return contents;
  }

  public List<String> getImageUrls() {
    return imageUrls;
  }

  public ArticleEntity toEntity(Long userId){
    return new ArticleEntity(userId, this.getTitle(), this.getContents());
  }
}
