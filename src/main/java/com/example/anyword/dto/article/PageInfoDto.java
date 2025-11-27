package com.example.anyword.dto.article;

import lombok.Getter;

@Getter
public class PageInfoDto {

  private Integer nextPage;
  private Boolean hasNext;
  private Integer pageSize;

  public PageInfoDto() {}

  public PageInfoDto(Integer nextPage, Boolean hasNext, Integer pageSize) {
    this.nextPage = nextPage;
    this.hasNext = hasNext;
    this.pageSize = pageSize;
  }


}