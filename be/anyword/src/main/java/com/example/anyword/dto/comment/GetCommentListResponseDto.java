package com.example.anyword.dto.comment;
import java.util.List;

public class GetCommentListResponseDto {
  private Long articleId;
  private List<CommentItemDto> items;

  public GetCommentListResponseDto(){};

  public GetCommentListResponseDto(Long postId, List<CommentItemDto> items) {
    this.articleId = postId;
    this.items = items;
  }



  public Long getArticleId() {
    return articleId;
  }

  public void setArticleId(Long articleId) {
    this.articleId = articleId;
  }

  public List<CommentItemDto> getItems() {
    return items;
  }

  public void setItems(List<CommentItemDto> items) {
    this.items = items;
  }


}
