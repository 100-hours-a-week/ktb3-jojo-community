package com.example.anyword.Article.mapper;

import com.example.anyword.Article.dto.ArticleListItemDto;
import com.example.anyword.Article.dto.ArticleStatusInfoDto;
import com.example.anyword.Article.dto.AuthorInfoDto;
import com.example.anyword.Article.dto.PageInfoDto;
import com.example.anyword.Article.dto.response.GetArticleListResponseDto;
import com.example.anyword.Article.dto.response.GetArticleResponseDto;
import com.example.anyword.Article.dto.response.PostArticleResponseDto;
import com.example.anyword.Article.dto.response.PutArticleResponseDto;
import com.example.anyword.Article.entity.ArticleEntity;
import java.util.List;

public interface ArticleMapper {

  GetArticleResponseDto toGetArticleResponse(
      ArticleEntity article,
      AuthorInfoDto author,
      ArticleStatusInfoDto status,
      boolean likedByMe,
      boolean isMyContents
  );

  /** 게시글 작성 응답 매핑 */
  PostArticleResponseDto toPostArticleResponse(ArticleEntity saved);

  /** 게시글 수정 응답 매핑 */
  PutArticleResponseDto toPutArticleResponse(ArticleEntity updated);

  /** 게시글 목록 응답 매핑 (리스트 아이템은 이미 구성되어 있다고 가정) */
  GetArticleListResponseDto toGetArticleListResponse(
      List<ArticleListItemDto> items,
      PageInfoDto pageInfoDto
  );

}
