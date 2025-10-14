package com.example.anyword.dto.comment;

import jakarta.validation.constraints.NotBlank;

public class CommentRequestDto {
  @NotBlank
  private String content;

  public CommentRequestDto(){};

  public CommentRequestDto(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
