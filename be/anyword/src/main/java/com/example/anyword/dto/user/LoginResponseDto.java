package com.example.anyword.dto.user;

import com.example.anyword.entity.UserEntity;

public class LoginResponseDto {
  //TODO: Jwt 추가 후 accessToken 추가
  private final UserEntity user;

  public LoginResponseDto(UserEntity user){
    this.user = user;
  }

  public UserEntity getUser() {
    return user;
  }
}
