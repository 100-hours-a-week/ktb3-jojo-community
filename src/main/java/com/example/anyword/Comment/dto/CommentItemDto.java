package com.example.anyword.Comment.dto;

import com.example.anyword.Article.dto.AuthorInfoDto;
import com.example.anyword.Comment.entity.CommentEntity;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

}
