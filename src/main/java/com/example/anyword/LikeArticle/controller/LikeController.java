package com.example.anyword.LikeArticle.controller;

import static com.example.anyword.shared.constants.ResponseMessage.SUCCESS;

import com.example.anyword.aop.Authable;
import com.example.anyword.shared.dto.BaseResponseDto;
import com.example.anyword.security.customUserDetails.CustomUserDetails;
import com.example.anyword.LikeArticle.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/like")
public class LikeController {

  private final LikeService likeService;

  public LikeController(LikeService likeService) {
    this.likeService = likeService;
  }

  @PostMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<?>> addLike(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long articleId
  ){
    Long userId = userDetails.getUser().getId();

    likeService.addLike(articleId, userId);

    return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseDto<>(SUCCESS));
  }



  @DeleteMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<?>> removeLike(@AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long articleId){
    Long userId = userDetails.getUser().getId();

    likeService.removeLike(articleId, userId);
    
    return ResponseEntity.ok(new BaseResponseDto<>(SUCCESS));
  }

}
