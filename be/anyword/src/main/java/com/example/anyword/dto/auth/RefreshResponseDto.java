package com.example.anyword.dto.auth;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshResponseDto {
  private String accessToken;
}
