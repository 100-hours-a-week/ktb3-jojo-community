package com.example.anyword.dto.article.response;

import com.example.anyword.dto.article.ArticleStatusInfo;
import com.example.anyword.dto.article.AuthorInfo;
import com.example.anyword.entity.ArticleEntity;
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

  private AuthorInfo author;

  private ArticleStatusInfo status;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private List<String> imageUrls;

  private Boolean likedByMe;

  private Boolean isMyContents;
}
