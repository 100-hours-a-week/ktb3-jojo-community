package com.example.anyword.controller;

import static com.example.anyword.shared.constants.Key.SESSION_USER_ID;
import static com.example.anyword.shared.constants.ResponseMessage.COMMENT_CREATED_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.COMMENT_DELETE_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.COMMENT_GET_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.COMMENT_UPDATED_SUCCESS;

import com.example.anyword.aop.Authable;
import com.example.anyword.dto.BaseResponseDto;
import com.example.anyword.dto.comment.CommentItemDto;
import com.example.anyword.dto.comment.CommentRequestDto;
import com.example.anyword.dto.comment.CreateCommentResponseDto;
import com.example.anyword.dto.comment.GetCommentListResponseDto;
import com.example.anyword.service.CommentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/comments")
@Validated
public class CommentController {
  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }


  @Authable
  @GetMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<GetCommentListResponseDto>> getComments(
      HttpSession session,
      @PathVariable Long articleId) {
    Long currentUserId = (Long) session.getAttribute(SESSION_USER_ID);
    GetCommentListResponseDto data = commentService.getCommentsList(articleId, currentUserId);

    return ResponseEntity.ok(new BaseResponseDto<>(COMMENT_GET_SUCCESS, data));
  }


  @Authable
  @PostMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<CommentItemDto>> createComment(
      HttpSession session,
      @PathVariable Long articleId,
      @Valid @RequestBody CommentRequestDto request
      ) {
    Long userId = (Long) session.getAttribute(SESSION_USER_ID);
    CommentItemDto response = commentService.createComment(articleId, userId, request);

    return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseDto<>(COMMENT_CREATED_SUCCESS, response));
  }


  @Authable
  @PutMapping("/{commentId}")
  public ResponseEntity<BaseResponseDto<CreateCommentResponseDto>> updateComment(
      HttpSession session,
      @PathVariable Long commentId,
      @Valid @RequestBody CommentRequestDto request) {

    Long userId = (Long) session.getAttribute(SESSION_USER_ID);

    CreateCommentResponseDto response = commentService.updateComment(commentId, userId, request);
    return ResponseEntity.ok(new BaseResponseDto<>(COMMENT_UPDATED_SUCCESS, response));
  }

  @Authable
  @DeleteMapping("/{commentId}")
  public ResponseEntity<BaseResponseDto<?>> deleteComment(
      HttpSession session,
      @PathVariable Long commentId) {

    Long userId = (Long) session.getAttribute(SESSION_USER_ID);

    commentService.deleteComment(commentId, userId);

    return ResponseEntity.ok(new BaseResponseDto<>(COMMENT_DELETE_SUCCESS));
  }
}
