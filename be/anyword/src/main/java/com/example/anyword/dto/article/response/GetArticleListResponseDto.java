package com.example.anyword.dto.article.response;

import com.example.anyword.dto.article.ArticleListItem;
import com.example.anyword.dto.article.PageInfo;
import java.util.List;

public class GetArticleListResponseDto {

  private List<ArticleListItem> items;
  private PageInfo pageInfo;

  public GetArticleListResponseDto() {}

  public GetArticleListResponseDto(List<ArticleListItem> items, PageInfo pageInfo) {
    this.items = items;
    this.pageInfo = pageInfo;
  }

  public List<ArticleListItem> getItems() {
    return items;
  }


  public PageInfo getPageInfo() {
    return pageInfo;
  }

}