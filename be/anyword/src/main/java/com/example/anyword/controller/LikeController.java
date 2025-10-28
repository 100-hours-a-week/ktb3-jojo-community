package com.example.anyword.controller;

import static com.example.anyword.shared.constants.Key.SESSION_USER_ID;
import static com.example.anyword.shared.constants.ResponseMessage.SUCCESS;

import com.example.anyword.aop.Authable;
import com.example.anyword.dto.BaseResponseDto;
import com.example.anyword.service.LikeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @Authable
  @PostMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<?>> addLike(
      HttpSession session,
      @PathVariable Long articleId
  ){
    Long userId = (Long) session.getAttribute(SESSION_USER_ID);

    likeService.addLike(articleId, userId);

    return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseDto<>(SUCCESS));
  }


  @Authable
  @DeleteMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<?>> removeLike(HttpSession session,
      @PathVariable Long articleId){
    Long userId = (Long) session.getAttribute(SESSION_USER_ID);

    likeService.removeLike(articleId, userId);
    
    return ResponseEntity.ok(new BaseResponseDto<>(SUCCESS));
  }

}
