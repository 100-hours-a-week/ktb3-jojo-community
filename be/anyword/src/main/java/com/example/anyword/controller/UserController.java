package com.example.anyword.controller;

import com.example.anyword.dto.BaseResponseDto;
import com.example.anyword.dto.user.LoginRequestDto;
import com.example.anyword.dto.user.LoginResponseDto;
import com.example.anyword.dto.user.PutUserRequestDto;
import com.example.anyword.dto.user.SignupResponseDto;
import com.example.anyword.entity.UserEntity;
import com.example.anyword.shared.constants.ResponseMessage;
import com.example.anyword.dto.user.SignupRequestDto;
import com.example.anyword.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<BaseResponseDto<LoginResponseDto>> login(
    @Valid
    @RequestBody LoginRequestDto request,
      HttpSession session
  ){
    UserEntity user = service.login(request);
    session.setAttribute("userId", user.getId());
    LoginResponseDto response = new LoginResponseDto(user);

    return ResponseEntity.ok(new BaseResponseDto<>(ResponseMessage.LOGIN_SUCCESS, response));
  }

  @GetMapping("/current")
  public ResponseEntity<?> currentUser(HttpSession session){
    UserEntity user = service.getUserFromSession(session);

    return ResponseEntity.ok(new BaseResponseDto<>(ResponseMessage.SUCCESS, user));
  }

  @PutMapping("/current")
  public ResponseEntity<?> patchUserInfo(@Valid @RequestBody PutUserRequestDto request,
      HttpSession session){
    UserEntity updatedUser = service.putUser(session, request);

    return ResponseEntity.ok(new BaseResponseDto<>(ResponseMessage.SUCCESS, updatedUser));
  }

  @DeleteMapping("/current/signout")
  public ResponseEntity<?> signOut(HttpSession session){
    service.signout(session);

    return ResponseEntity.ok(new BaseResponseDto<>(ResponseMessage.SUCCESS));
  }

}
