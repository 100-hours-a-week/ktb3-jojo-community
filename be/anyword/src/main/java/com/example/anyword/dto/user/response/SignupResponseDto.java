package com.example.anyword.dto.user.response;

public class SignupResponseDto {
  private final Long userId;

  public SignupResponseDto(Long userId){
    this.userId = userId;
  }

  public Long getUserId() {
    return userId;
  }
}
