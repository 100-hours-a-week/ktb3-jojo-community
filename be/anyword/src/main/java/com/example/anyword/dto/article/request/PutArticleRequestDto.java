package com.example.anyword.dto.article.request;

import com.example.anyword.entity.ArticleEntity;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Getter
public class PutArticleRequestDto {
  @Size(max = 26)
  private String title;

  private String contents;

  //nullable
  private List<String> imageUrls;

  public PutArticleRequestDto() {}


}
