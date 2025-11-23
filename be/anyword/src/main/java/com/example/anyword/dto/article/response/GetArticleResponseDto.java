package com.example.anyword.dto.article.response;

import com.example.anyword.dto.article.ArticleStatusInfoDto;
import com.example.anyword.dto.article.AuthorInfoDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetArticleResponseDto {
  private Long articleId;

  private String title;

  private String contents;

  private AuthorInfoDto author;

  private ArticleStatusInfoDto status;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private List<String> imageUrls;

  private Boolean likedByMe;

  private Boolean isMyContents;
}
