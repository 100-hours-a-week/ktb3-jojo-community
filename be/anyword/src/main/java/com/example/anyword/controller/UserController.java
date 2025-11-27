package com.example.anyword.controller;

import static com.example.anyword.shared.constants.ResponseMessage.LOGIN_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.LOGOUT_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.SIGNUP_SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.SUCCESS;
import static com.example.anyword.shared.constants.ResponseMessage.UNAUTHORIZED;

import com.example.anyword.dto.BaseResponseDto;
import com.example.anyword.dto.user.request.LoginRequestDto;
import com.example.anyword.dto.user.response.LoginResponseDto;
import com.example.anyword.dto.user.response.UserResponseDto;
import com.example.anyword.dto.user.request.PutUserRequestDto;
import com.example.anyword.dto.user.response.SignupResponseDto;
import com.example.anyword.dto.user.request.SignupRequestDto;
import com.example.anyword.security.CustomUserDetails;
import com.example.anyword.service.UserService;
import com.example.anyword.shared.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    SignupResponseDto response = service.signup(request);

    return ResponseEntity.created(URI.create("api/user/current")).body(new BaseResponseDto<>(SIGNUP_SUCCESS, response));
  }

  @PostMapping("/login")
  public ResponseEntity<BaseResponseDto<LoginResponseDto>> login(
    @Valid
    @RequestBody LoginRequestDto request,
      HttpServletResponse response
  ){
    LoginResponseDto user = service.login(request, response);

    return ResponseEntity.ok(new BaseResponseDto<>(LOGIN_SUCCESS, user));
  }


  @GetMapping("/current")
  public ResponseEntity<BaseResponseDto<UserResponseDto>> currentUser(@AuthenticationPrincipal CustomUserDetails userDetails){
    UserResponseDto user = service.getCurrentUser(userDetails.getUser());

    return ResponseEntity.ok(new BaseResponseDto<>(SUCCESS, user));
  }


  @PutMapping("/current")
  public ResponseEntity<BaseResponseDto<UserResponseDto>> patchUserInfo(@Valid @RequestBody PutUserRequestDto request,
      @AuthenticationPrincipal CustomUserDetails userDetails){
    UserResponseDto updatedUser = service.putUser(userDetails.getUser(), request);

    return ResponseEntity.ok(new BaseResponseDto<>(SUCCESS, updatedUser));
  }


  @PostMapping("/current/logout")
  public ResponseEntity<?> logout(@AuthenticationPrincipal CustomUserDetails userDetails){
    try{
      service.logout(userDetails.getUser());
    } catch (Exception e) {
      throw new UnauthorizedException(UNAUTHORIZED);
    }
    return ResponseEntity.ok(new BaseResponseDto<>(LOGOUT_SUCCESS));
  }

  @DeleteMapping("/current/signout")
  public ResponseEntity<?> signOut(@AuthenticationPrincipal CustomUserDetails userDetails){
    service.signout(userDetails.getUser());

    return ResponseEntity.noContent().build();
  }
}
