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
import com.example.anyword.Article.entity.ArticleImageEntity;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapperIpl implements ArticleMapper {
  /** 게시글 단건 조회 응답 매핑 */
  @Override
  public GetArticleResponseDto toGetArticleResponse(
      ArticleEntity article,
      AuthorInfoDto author,
      ArticleStatusInfoDto status,
      boolean likedByMe,
      boolean isMyContents
  ) {
    if (article == null) return null;

    List<String> imageUrls = article.getArticleImageEntities() == null ? List.of()
        : article.getArticleImageEntities().stream().map(ArticleImageEntity::getImageURL).toList();


    return  new GetArticleResponseDto(
        article.getId(),
        article.getTitle(),
        article.getContents(),
        author,
        status,
        article.getCreatedAt(),
        article.getUpdatedAt(),
        imageUrls,
        likedByMe,
        isMyContents
    );
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
      List<ArticleListItemDto> items,
      PageInfoDto pageInfoDto
  ) {
    return new GetArticleListResponseDto(
        items == null ? List.of() : items,
        pageInfoDto
    );
  }



}
