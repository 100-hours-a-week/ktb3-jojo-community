package com.example.anyword.dto.article;

public class PageInfo {

  private Integer nextPage;
  private Boolean hasNext;
  private Integer pageSize;

  public PageInfo() {};

  public PageInfo(Integer nextPage, Boolean hasNext, Integer pageSize) {
    this.nextPage = nextPage;
    this.hasNext = hasNext;
    this.pageSize = pageSize;
  }

  public Integer getNextPage() {
    return nextPage;
  }


  public Boolean getHasNext() {
    return hasNext;
  }


  public Integer getPageSize() {
    return pageSize;
  }
}