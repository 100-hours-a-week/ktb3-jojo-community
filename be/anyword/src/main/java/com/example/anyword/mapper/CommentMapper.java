package com.example.anyword.mapper;
import com.example.anyword.dto.comment.CommentItem;
import com.example.anyword.dto.comment.CreateCommentResponseDto;
import com.example.anyword.dto.comment.GetCommentListResponseDto;
import com.example.anyword.entity.CommentEntity;
import java.util.List;

public interface CommentMapper {
  /**
   * CommentEntity → CommentItem
   */
  CommentItem toItem(CommentEntity comment, Long currentUserId);

  /**
   * CommentEntity 리스트 → CommentItem 리스트
   */
  List<CommentItem> toItems(List<CommentEntity> comments, Long currentUserId);

  /**
   * commentEntity -> put/create DTO 로 변환
   */
  CreateCommentResponseDto toResponse(CommentEntity comment);

  /**
   * 전체 응답 DTO로 변환
   */
  GetCommentListResponseDto toListResponse(Long articleId, List<CommentEntity> comments, Long currentUserId);

}
