package com.example.anyword.controller;

import com.example.anyword.dto.BaseResponseDto;
import com.example.anyword.dto.article.GetArticleResponseDto;
import com.example.anyword.dto.article.PostArticleRequestDto;
import com.example.anyword.dto.article.PostArticleResponseDto;
import com.example.anyword.dto.article.PutArticleRequestDto;
import com.example.anyword.dto.article.PutArticleResponseDto;
import com.example.anyword.service.ArticleService;
import com.example.anyword.service.UserService;
import com.example.anyword.shared.constants.ResponseMessage;
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
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/article")
@Validated
public class ArticleController {

  private final ArticleService articleService;
  private final UserService userService;

  public ArticleController(ArticleService articleService,
      UserService userService) {
    this.articleService = articleService;
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<BaseResponseDto<PostArticleResponseDto>> createArticle(
      @Valid @RequestBody PostArticleRequestDto request, HttpSession session){
    Long userId = userService.getUserIdFromSession(session);
    Long articleId = articleService.createArticle(userId, request);

    return ResponseEntity
        .created(URI.create("/api/article/"+articleId))
        .body(new BaseResponseDto<>(ResponseMessage.ARTICLE_CREATE_SUCCESS, new PostArticleResponseDto(articleId)));
  }

  @GetMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<GetArticleResponseDto>> getArticle(
      HttpSession session,
      @PathVariable Long articleId) {

    Long userId = userService.getUserIdFromSession(session);
    GetArticleResponseDto data = articleService.getArticle(articleId, userId);

    return ResponseEntity.ok(new BaseResponseDto<>(ResponseMessage.ARTICLE_GET_SUCCESS, data));
  }

  @PutMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<PutArticleResponseDto>> createArticle(
      @Valid @RequestBody PutArticleRequestDto request,
      HttpSession session,
      @PathVariable Long articleId){
    Long userId = userService.getUserIdFromSession(session);
    articleService.putArticle(userId, articleId, request);

    return ResponseEntity.ok(new BaseResponseDto<>(ResponseMessage.ARTICLE_UPDATE_SUCCESS, new PutArticleResponseDto(articleId)));
  }

  @DeleteMapping("/{articleId}")
  public ResponseEntity<?> deleteArticle(
      HttpSession session,
      @PathVariable Long articleId) {

    Long userId = userService.getUserIdFromSession(session);
    articleService.deleteArticle(userId, articleId);

    return ResponseEntity.noContent().build();
  }

}
