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
import org.springframework.stereotype.Component;

@Component
public class ArticleMapperIpl implements ArticleMapper {
  /** 게시글 단건 조회 응답 매핑 */
  @Override
  public GetArticleResponseDto toGetArticleResponse(
      ArticleEntity article,
      AuthorInfo author,
      ArticleStatusInfo status,
      boolean likedByMe,
      boolean isMyContents,
      @Nullable List<String> imageUrls
  ) {
    if (article == null) return null;
    return GetArticleResponseDto.from(article, author, status, likedByMe, isMyContents,
        imageUrls == null ? List.of() : imageUrls);
  }


  /** 게시글 작성 응답 매핑 */
  @Override
  public PostArticleResponseDto toPostArticleResponse(ArticleEntity saved) {
    if (saved == null) return null;
    return new PostArticleResponseDto(saved.getId());
  }

  /** 게시글 수정 응답 매핑 */
  @Override
  public PutArticleResponseDto toPutArticleResponse(ArticleEntity updated) {
    if (updated == null) return null;
    return new PutArticleResponseDto(updated.getId());
  }

  /** 게시글 목록 응답 매핑 (리스트 아이템은 이미 구성되어 있다고 가정) */
  @Override
  public GetArticleListResponseDto toGetArticleListResponse(
      List<ArticleListItem> items,
      PageInfo pageInfo
  ) {
    return new GetArticleListResponseDto(
        items == null ? List.of() : items,
        pageInfo
    );
  }
}
