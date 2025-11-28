package com.example.anyword.Auth.service;

import com.example.anyword.Auth.dto.RefreshResponseDto;
import com.example.anyword.Auth.entity.RefreshTokenEntity;
import com.example.anyword.Auth.repository.RefreshTokenRepository;
import com.example.anyword.security.JWTUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final RefreshTokenRepository refreshTokenRepository;
  private final JWTUtil jwtUtil;

  public AuthService(RefreshTokenRepository refreshTokenRepository, JWTUtil jwtUtil) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.jwtUtil = jwtUtil;
  }


  /*
  * db 에서 revoked 확인
  */
  private Boolean isRefreshTokenRevoked(String refreshToken){
    RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByTokenHash(refreshToken)
        .orElseThrow(() -> new JwtException("refreshToken 을 찾지 못했습니다."));

    return refreshTokenEntity.getRevoked();
  }


  public RefreshResponseDto createAccessToken(String refreshToken){
    //repository 에서 확인 (revoked 여부)
    if (isRefreshTokenRevoked(refreshToken)) throw new JwtException("refreshToken revoked");

    return new RefreshResponseDto(jwtUtil.reissueAccessToken(refreshToken));
  }
}
