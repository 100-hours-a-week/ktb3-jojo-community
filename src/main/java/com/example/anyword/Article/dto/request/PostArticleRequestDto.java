package com.example.anyword.Article.dto.request;

import com.example.anyword.shared.constants.ValidErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Getter
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
}
