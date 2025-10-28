package com.example.anyword.mapper;

import com.example.anyword.dto.article.ArticleListItem;
import com.example.anyword.dto.article.ArticleStatusInfo;
import com.example.anyword.dto.article.AuthorInfo;
import com.example.anyword.dto.article.PageInfo;
import com.example.anyword.dto.article.response.GetArticleListResponseDto;
import com.example.anyword.dto.article.response.GetArticleResponseDto;
import com.example.anyword.dto.article.response.PostArticleResponseDto;
import com.example.anyword.dto.article.response.PutArticleResponseDto;
import com.example.anyword.entity.ArticleEntity;
import io.micrometer.common.lang.Nullable;
import java.util.List;

public interface ArticleMapper {

  GetArticleResponseDto toGetArticleResponse(
      ArticleEntity article,
      AuthorInfo author,
      ArticleStatusInfo status,
      boolean likedByMe,
      boolean isMyContents,
      @Nullable List<String> imageUrls
  );

  /** 게시글 작성 응답 매핑 */
  PostArticleResponseDto toPostArticleResponse(ArticleEntity saved);

  /** 게시글 수정 응답 매핑 */
  PutArticleResponseDto toPutArticleResponse(ArticleEntity updated);

  /** 게시글 목록 응답 매핑 (리스트 아이템은 이미 구성되어 있다고 가정) */
  GetArticleListResponseDto toGetArticleListResponse(
      List<ArticleListItem> items,
      PageInfo pageInfo
  );

}
