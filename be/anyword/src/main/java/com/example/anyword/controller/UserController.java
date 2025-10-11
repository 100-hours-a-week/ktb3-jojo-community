package com.example.anyword.controller;

import com.example.anyword.dto.user.BaseResponseDto;
import com.example.anyword.dto.user.LoginRequestDto;
import com.example.anyword.dto.user.LoginResponseDto;
import com.example.anyword.dto.user.SignupResponseDto;
import com.example.anyword.entity.UserEntity;
import com.example.anyword.shared.constants.ResponseMessage;
import com.example.anyword.dto.user.SignupRequestDto;
import com.example.anyword.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: 개발 완료 후 구체 response 추가

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {
  private final UserService service;

  public UserController(UserService service){
    this.service = service;
  }

  @PostMapping("/signup")
  public ResponseEntity<BaseResponseDto<SignupResponseDto>> signup(
      @Valid
      @RequestBody SignupRequestDto request
  ){

    UserEntity user = service.signup(request);
    SignupResponseDto response = new SignupResponseDto(user.getId());


    return ResponseEntity.ok(new BaseResponseDto<>(ResponseMessage.SIGNUP_SUCCESS, response));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(
    @Valid
    @RequestBody LoginRequestDto request,
      HttpSession session
  ){
    UserEntity user = service.login(request);
    session.setAttribute("userId", user.getId());
    LoginResponseDto response = new LoginResponseDto(user);

    return ResponseEntity.ok(new BaseResponseDto<>(ResponseMessage.LOGIN_SUCCESS, response));
  }

}
