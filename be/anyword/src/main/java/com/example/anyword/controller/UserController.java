package com.example.anyword.controller;

import com.example.anyword.dto.user.LoginRequestDto;
import com.example.anyword.shared.constants.ResponseMessage;
import com.example.anyword.dto.user.CreateUserDto;
import com.example.anyword.service.UserService;
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
  public ResponseEntity<?> signup(
      @Valid
      @RequestBody CreateUserDto request
  ){

    service.signup(request);

    return ResponseEntity.ok(ResponseMessage.SIGNUP_SUCCESS);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(
    @Valid
    @RequestBody LoginRequestDto request
  ){
    service.login(request);
    return ResponseEntity.ok(ResponseMessage.LOGIN_SUCCESS);
  }

}
