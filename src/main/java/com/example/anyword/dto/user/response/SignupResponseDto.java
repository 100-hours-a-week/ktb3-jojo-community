package com.example.anyword.dto.user.response;

import lombok.Getter;

@Getter
public class SignupResponseDto {
  private final Long userId;

  public SignupResponseDto(Long userId){
    this.userId = userId;
  }

}
