package com.example.anyword.dto.comment;

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
