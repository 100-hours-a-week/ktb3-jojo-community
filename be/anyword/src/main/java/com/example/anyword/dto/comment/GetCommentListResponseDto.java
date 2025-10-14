package com.example.anyword.dto.comment;
import java.util.List;

public class GetCommentListResponseDto {
  private Long articleId;
  private List<CommentItem> items;

  public GetCommentListResponseDto(){};

  public GetCommentListResponseDto(Long postId, List<CommentItem> items) {
    this.articleId = postId;
    this.items = items;
  }



  public Long getArticleId() {
    return articleId;
  }

  public void setArticleId(Long articleId) {
    this.articleId = articleId;
  }

  public List<CommentItem> getItems() {
    return items;
  }

  public void setItems(List<CommentItem> items) {
    this.items = items;
  }


}
