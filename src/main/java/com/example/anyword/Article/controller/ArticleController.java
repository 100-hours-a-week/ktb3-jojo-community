package com.example.anyword.Article.controller;


import static com.example.anyword.shared.constants.ResponseMessage.ARTICLE_CREATE_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.ARTICLE_GET_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.ARTICLE_UPDATE_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.SUCCESS;

import com.example.anyword.shared.dto.BaseResponseDto;
import com.example.anyword.Article.dto.response.GetArticleListResponseDto;
import com.example.anyword.Article.dto.response.GetArticleResponseDto;
import com.example.anyword.Article.dto.request.PostArticleRequestDto;
import com.example.anyword.Article.dto.response.PostArticleResponseDto;
import com.example.anyword.Article.dto.request.PutArticleRequestDto;
import com.example.anyword.Article.dto.response.PutArticleResponseDto;
import com.example.anyword.security.customUserDetails.CustomUserDetails;
import com.example.anyword.Article.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/article")
@Validated
public class ArticleController {

  private final ArticleService articleService;

  public ArticleController(ArticleService articleService) {
    this.articleService = articleService;
  }


  @PostMapping
  public ResponseEntity<BaseResponseDto<PostArticleResponseDto>> createArticle(
      @Valid @RequestBody PostArticleRequestDto request, @AuthenticationPrincipal CustomUserDetails userDetails){
    Long userId = userDetails.getUser().getId();
    PostArticleResponseDto response = articleService.createArticle(userId, request);


    return ResponseEntity.ok(new BaseResponseDto<>(ARTICLE_CREATE_SUCCESS, response));
  }

  @GetMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<GetArticleResponseDto>> getArticle(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long articleId) {

    Long userId = userDetails.getUser().getId();
    GetArticleResponseDto data = articleService.getArticle(articleId, userId);

    return ResponseEntity.ok(new BaseResponseDto<>(ARTICLE_GET_SUCCESS, data));
  }

  /**
   * 게시글 목록 조회
   * @param currentPage 현재 페이지 (기본값 1)
   * @param pageSize 페이지당 항목 수 (기본값 20)
   * @param sort 정렬 방식 latest | popular
   * @return 게시글 목록
   */
  @GetMapping
  public ResponseEntity<BaseResponseDto<GetArticleListResponseDto>> getArticles(
      @RequestParam(required = false) Integer currentPage,
      @RequestParam(required = false) Integer pageSize,
      @RequestParam(required = false) String sort) {

    GetArticleListResponseDto data = articleService.getArticleList(currentPage, pageSize, sort);

    return ResponseEntity.ok(new BaseResponseDto<>(SUCCESS, data));
  }


  @PutMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<PutArticleResponseDto>> putArticle(
      @Valid @RequestBody PutArticleRequestDto request,
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long articleId){
    Long userId = userDetails.getUser().getId();
    PutArticleResponseDto response = articleService.putArticle(userId, articleId, request);

    return ResponseEntity.ok(new BaseResponseDto<>(ARTICLE_UPDATE_SUCCESS, response));
  }


  @DeleteMapping("/{articleId}")
  public ResponseEntity<?> deleteArticle(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long articleId) {

    Long userId = userDetails.getUser().getId();
    articleService.deleteArticle(userId, articleId);

    return ResponseEntity.noContent().build();
  }

}
