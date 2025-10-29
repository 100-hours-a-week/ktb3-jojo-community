package com.example.anyword.dto.comment;

import com.example.anyword.dto.article.AuthorInfoDto;
import com.example.anyword.entity.CommentEntity;
import java.time.LocalDateTime;

public class CommentItemDto {
  private Long commentId;
  private String content;
  private AuthorInfoDto author;
  private boolean editable;
  private LocalDateTime createdAt;

  public CommentItemDto() {}

  public CommentItemDto(Long commentId, String content, AuthorInfoDto author, boolean editable, LocalDateTime createdAt){
    this.commentId = commentId;
    this.content = content;
    this.author = author;
    this.editable = editable;
    this.createdAt = createdAt;
  };

  public static CommentItemDto from(CommentEntity comment, AuthorInfoDto author, boolean editable, LocalDateTime createdAt) {
    return new CommentItemDto(
        comment.getId(),
        comment.getContents(),
        author,
        editable,
        createdAt
    );
  }

  public Long getCommentId() {
    return commentId;
  }

  public void setCommentId(Long commentId) {
    this.commentId = commentId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public AuthorInfoDto getAuthor() {
    return author;
  }

  public void setAuthor(AuthorInfoDto author) {
    this.author = author;
  }

  public boolean isEditable() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
