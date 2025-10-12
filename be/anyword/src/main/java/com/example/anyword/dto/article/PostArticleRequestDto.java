package com.example.anyword.dto.article;

import com.example.anyword.entity.ArticleEntity;
import com.example.anyword.shared.constants.ValidErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class PostArticleRequestDto {
  @NotBlank(message=ValidErrorMessage.NO_TITLE_OR_CONTENTS)
  @Size(max = 26)
  private final String title;

  @NotBlank(message = ValidErrorMessage.NO_TITLE_OR_CONTENTS)
  private final String content;

  private final List<String> imageUrls;

  public PostArticleRequestDto(String title, String content, List<String> imageUrls) {
    this.title = title;
    this.content = content;
    this.imageUrls = imageUrls;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public List<String> getImageUrls() {
    return imageUrls;
  }

  public ArticleEntity toEntity(Long userId){
    return new ArticleEntity(userId, this.getTitle(), this.getContent());
  }
}
