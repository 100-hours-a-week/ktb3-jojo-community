package com.example.anyword.dto.article.response;

import com.example.anyword.dto.article.ArticleListItemDto;
import com.example.anyword.dto.article.PageInfoDto;
import java.util.List;

public class GetArticleListResponseDto {

  private List<ArticleListItemDto> items;
  private PageInfoDto pageInfoDto;

  public GetArticleListResponseDto() {}

  public GetArticleListResponseDto(List<ArticleListItemDto> items, PageInfoDto pageInfoDto) {
    this.items = items;
    this.pageInfoDto = pageInfoDto;
  }

  public List<ArticleListItemDto> getItems() {
    return items;
  }


  public PageInfoDto getPageInfo() {
    return pageInfoDto;
  }

}