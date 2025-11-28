package com.example.anyword.Comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequestDto {
  @NotBlank
  private String content;

  public CommentRequestDto(){}

  public CommentRequestDto(String content) {
    this.content = content;
  }

}
