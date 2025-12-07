package com.example.anyword.User.dto.response;

import lombok.Getter;

@Getter
public class SignupResponseDto {
  private final Long userId;

  public SignupResponseDto(Long userId){
    this.userId = userId;
  }

}
