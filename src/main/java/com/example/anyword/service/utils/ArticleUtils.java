package com.example.anyword.service.utils;

import com.example.anyword.shared.constants.PageConstants;

public class ArticleUtils {
  /**
   * 페이지 번호 검증
   */
  public static int validatePage(Integer page) {
    if (page == null || page < 1) {
      return PageConstants.DEFAULT_PAGE;
    }
    return page;
  }

  /**
   * 페이지 크기 검증
   */
  public static int validatePageSize(Integer size) {
    if (size == null || size < 1) {
      return PageConstants.DEFAULT_PAGE_SIZE;
    }
    if (size > PageConstants.MAX_PAGE_SIZE) {
      return PageConstants.MAX_PAGE_SIZE;
    }
    return size;
  }

  /**
   * 정렬 방식 검증
   */
  public static String validateSort(String sort) {
    if (sort == null || (!sort.equals("latest") && !sort.equals("popular"))) {
      return PageConstants.SORT_LATEST;
    }
    return sort;
  }


  public static  <T> T merge(T newValue, T originalValue) {
    return newValue != null ? newValue : originalValue;
  }
}
