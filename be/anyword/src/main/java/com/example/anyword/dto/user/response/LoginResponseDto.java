package com.example.anyword.dto.user.response;

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