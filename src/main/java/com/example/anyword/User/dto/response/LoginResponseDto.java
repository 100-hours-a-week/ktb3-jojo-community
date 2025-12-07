package com.example.anyword.User.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginResponseDto {
  private final Long id;
  private final String email;
  private final String nickname;
  private final String profileImageUrl;
  private final String accessToken;
}