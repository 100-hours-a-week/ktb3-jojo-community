package com.example.anyword.User.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserResponseDto {
  private final Long id;
  private final String email;
  private final String nickname;
  private final String profileImageUrl;
}