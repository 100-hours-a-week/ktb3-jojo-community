package com.example.anyword.controller;


import static com.example.anyword.shared.constants.ResponseMessage.REISSUED_ACCESSTOKEN;

import com.example.anyword.dto.BaseResponseDto;
import com.example.anyword.dto.auth.RefreshResponseDto;
import com.example.anyword.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {this.authService = authService;}

  @PostMapping("/refresh")
  public ResponseEntity<BaseResponseDto<RefreshResponseDto>> postRefresh(
      @CookieValue(name = "refreshToken", required = false) String refreshToken
  ){
    RefreshResponseDto response = authService.createAccessToken(refreshToken);

    return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseDto<>(REISSUED_ACCESSTOKEN, response));
  }

}
