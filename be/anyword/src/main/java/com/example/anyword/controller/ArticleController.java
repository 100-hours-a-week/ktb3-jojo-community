package com.example.anyword.controller;

import static com.example.anyword.shared.constants.Key.SESSION_USER_ID;
import static com.example.anyword.shared.constants.ResponseMessage.ARTICLE_CREATE_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.ARTICLE_GET_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.ARTICLE_UPDATE_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.SUCCESS;

import com.example.anyword.aop.Authable;
import com.example.anyword.dto.BaseResponseDto;
import com.example.anyword.dto.article.response.GetArticleListResponseDto;
import com.example.anyword.dto.article.response.GetArticleResponseDto;
import com.example.anyword.dto.article.request.PostArticleRequestDto;
import com.example.anyword.dto.article.response.PostArticleResponseDto;
import com.example.anyword.dto.article.request.PutArticleRequestDto;
import com.example.anyword.dto.article.response.PutArticleResponseDto;
import com.example.anyword.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
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

  @Authable
  @PostMapping
  public ResponseEntity<BaseResponseDto<PostArticleResponseDto>> createArticle(
      @Valid @RequestBody PostArticleRequestDto request, HttpSession session){
    Long userId = (Long) session.getAttribute(SESSION_USER_ID);
    PostArticleResponseDto response = articleService.createArticle(userId, request);


    return ResponseEntity.ok(new BaseResponseDto<>(ARTICLE_CREATE_SUCCESS, response));
  }

  @GetMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<GetArticleResponseDto>> getArticle(
      HttpSession session,
      @PathVariable Long articleId) {

    Long userId = (Long) session.getAttribute(SESSION_USER_ID);
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

  @Authable
  @PutMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<PutArticleResponseDto>> putArticle(
      @Valid @RequestBody PutArticleRequestDto request,
      HttpSession session,
      @PathVariable Long articleId){
    Long userId = (Long) session.getAttribute(SESSION_USER_ID);
    PutArticleResponseDto response = articleService.putArticle(userId, articleId, request);

    return ResponseEntity.ok(new BaseResponseDto<>(ARTICLE_UPDATE_SUCCESS, response));
  }

  @Authable
  @DeleteMapping("/{articleId}")
  public ResponseEntity<?> deleteArticle(
      HttpSession session,
      @PathVariable Long articleId) {

    Long userId = (Long) session.getAttribute(SESSION_USER_ID);
    articleService.deleteArticle(userId, articleId);

    return ResponseEntity.noContent().build();
  }

}
