package com.example.anyword.controller;

import static com.example.anyword.shared.constants.Key.SESSION_USER_ID;
import static com.example.anyword.shared.constants.ResponseMessage.COMMENT_CREATED_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.COMMENT_DELETE_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.COMMENT_GET_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.COMMENT_UPDATED_SUCCESS;

import com.example.anyword.dto.BaseResponseDto;
import com.example.anyword.dto.comment.CommentItemDto;
import com.example.anyword.dto.comment.CommentRequestDto;
import com.example.anyword.dto.comment.CreateCommentResponseDto;
import com.example.anyword.dto.comment.GetCommentListResponseDto;
import com.example.anyword.security.CustomUserDetails;
import com.example.anyword.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@Validated
public class CommentController {
  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }


  @GetMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<GetCommentListResponseDto>> getComments(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long articleId) {
    Long userId = userDetails.getUser().getId();
    GetCommentListResponseDto data = commentService.getCommentsList(articleId, userId);

    return ResponseEntity.ok(new BaseResponseDto<>(COMMENT_GET_SUCCESS, data));
  }


  @PostMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<CommentItemDto>> createComment(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long articleId,
      @Valid @RequestBody CommentRequestDto request
      ) {
    Long userId = userDetails.getUser().getId();
    CommentItemDto response = commentService.createComment(articleId, userId, request);

    return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseDto<>(COMMENT_CREATED_SUCCESS, response));
  }


  @PutMapping("/{commentId}")
  public ResponseEntity<BaseResponseDto<CreateCommentResponseDto>> updateComment(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long commentId,
      @Valid @RequestBody CommentRequestDto request) {

    Long userId = userDetails.getUser().getId();

    CreateCommentResponseDto response = commentService.updateComment(commentId, userId, request);
    return ResponseEntity.ok(new BaseResponseDto<>(COMMENT_UPDATED_SUCCESS, response));
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<BaseResponseDto<?>> deleteComment(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long commentId) {

    Long userId = userDetails.getUser().getId();

    commentService.deleteComment(commentId, userId);

    return ResponseEntity.ok(new BaseResponseDto<>(COMMENT_DELETE_SUCCESS));
  }
}
