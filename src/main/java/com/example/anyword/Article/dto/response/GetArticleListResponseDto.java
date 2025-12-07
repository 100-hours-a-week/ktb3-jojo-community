package com.example.anyword.Article.dto.response;

import com.example.anyword.Article.dto.ArticleListItemDto;
import com.example.anyword.Article.dto.PageInfoDto;
import java.util.List;
import lombok.Getter;

@Getter
public class GetArticleListResponseDto {

  private List<ArticleListItemDto> items;
  private PageInfoDto pageInfoDto;

  public GetArticleListResponseDto() {}

  public GetArticleListResponseDto(List<ArticleListItemDto> items, PageInfoDto pageInfoDto) {
    this.items = items;
    this.pageInfoDto = pageInfoDto;
  }

}