package com.example.anyword.controller;

import static com.example.anyword.shared.constants.ResponseMessage.COMMENT_CREATED_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.COMMENT_DELETE_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.COMMENT_GET_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.COMMENT_UPDATED_SUCCESS;

import com.example.anyword.dto.BaseResponseDto;
import com.example.anyword.dto.comment.CommentRequestDto;
import com.example.anyword.dto.comment.GetCommentListResponseDto;
import com.example.anyword.service.CommentService;
import com.example.anyword.service.UserService;
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
  private final UserService userService;

  public CommentController(CommentService commentService, UserService userService) {
    this.commentService = commentService;
    this.userService = userService;
  }


  @GetMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<GetCommentListResponseDto>> getComments(
      HttpSession session,
      @PathVariable Long articleId) {
    Long currentUserId = (Long) session.getAttribute("userId");
    GetCommentListResponseDto data = commentService.getCommentsList(articleId, currentUserId);

    return ResponseEntity.ok(new BaseResponseDto<>(COMMENT_GET_SUCCESS, data));
  }


  @PostMapping("/{articleId}")
  public ResponseEntity<BaseResponseDto<?>> createComment(
      HttpSession session,
      @PathVariable Long articleId,
      @Valid @RequestBody CommentRequestDto request
      ) {
    Long userId = userService.getUserIdFromSession(session);
    commentService.createComment(articleId, userId, request);

    return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseDto<>(COMMENT_CREATED_SUCCESS));
  }


  @PutMapping("/{commentId}")
  public ResponseEntity<BaseResponseDto<?>> updateComment(
      HttpSession session,
      @PathVariable Long commentId,
      @Valid @RequestBody CommentRequestDto request) {

    Long userId = userService.getUserIdFromSession(session);

    commentService.updateComment(commentId, userId, request);
    return ResponseEntity.ok(new BaseResponseDto<>(COMMENT_UPDATED_SUCCESS));
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<BaseResponseDto<?>> deleteComment(
      HttpSession session,
      @PathVariable Long commentId) {

    Long userId = userService.getUserIdFromSession(session);

    commentService.deleteComment(commentId, userId);

    return ResponseEntity.ok(new BaseResponseDto<>(COMMENT_DELETE_SUCCESS));
  }
}
