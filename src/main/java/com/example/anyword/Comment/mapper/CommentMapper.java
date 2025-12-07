package com.example.anyword.Comment.mapper;
import com.example.anyword.Comment.dto.CommentItemDto;
import com.example.anyword.Comment.dto.CreateCommentResponseDto;
import com.example.anyword.Comment.dto.GetCommentListResponseDto;
import com.example.anyword.Comment.entity.CommentEntity;
import java.util.List;

public interface CommentMapper {
  /**
   * CommentEntity → CommentItem
   */
  CommentItemDto toItem(CommentEntity comment, Long currentUserId);

  /**
   * CommentEntity 리스트 → CommentItem 리스트
   */
  List<CommentItemDto> toItems(List<CommentEntity> comments, Long currentUserId);

  /**
   * commentEntity -> put/create DTO 로 변환
   */
  CreateCommentResponseDto toResponse(CommentEntity comment);

  /**
   * 전체 응답 DTO로 변환
   */
  GetCommentListResponseDto toListResponse(Long articleId, List<CommentEntity> comments, Long currentUserId);

}
