package com.example.anyword.Comment.dto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetCommentListResponseDto {
  private Long articleId;
  private List<CommentItemDto> items;

  public GetCommentListResponseDto(){}

  public GetCommentListResponseDto(Long postId, List<CommentItemDto> items) {
    this.articleId = postId;
    this.items = items;
  }


}
