package com.example.anyword.controller;

import com.example.anyword.dto.BaseResponseDto;
import com.example.anyword.dto.article.PostArticleRequestDto;
import com.example.anyword.dto.article.PostArticleResponseDto;
import com.example.anyword.service.ArticleService;
import com.example.anyword.service.UserService;
import com.example.anyword.shared.constants.ResponseMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
  public ResponseEntity<BaseResponseDto<PostArticleResponseDto>> createArticle(@Valid @RequestBody PostArticleRequestDto request, HttpSession session){
    Long userId = userService.getUserIdFromSession(session);
    Long postId = articleService.createArticle(userId, request);

    return ResponseEntity.ok(new BaseResponseDto<>(ResponseMessage.SUCCESS, new PostArticleResponseDto(postId)));
  }

//  @PutMapping
//
//  @DeleteMapping





}
