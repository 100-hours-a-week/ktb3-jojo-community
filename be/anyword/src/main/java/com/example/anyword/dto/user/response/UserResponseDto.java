package com.example.anyword.dto.user.response;

import lombok.Getter;

@Getter
public class UserResponseDto {
  private final Long id;
  private final String email;
  private final String nickname;
  private final String profileImageUrl;

  //TODO: Jwt 추가 후 accessToken 추가

  public UserResponseDto(Long id, String email, String nickname, String profileImageUrl) {
    this.id = id;
    this.email = email;
    this.nickname = nickname;
    this.profileImageUrl = profileImageUrl;
  }

}