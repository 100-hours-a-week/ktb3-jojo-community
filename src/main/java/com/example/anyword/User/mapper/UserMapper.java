package com.example.anyword.User.mapper;

import com.example.anyword.User.dto.response.LoginResponseDto;
import com.example.anyword.User.dto.response.UserResponseDto;
import com.example.anyword.User.dto.response.SignupResponseDto;
import com.example.anyword.User.entity.UserEntity;

public interface UserMapper {
  UserResponseDto toUserResponseDto(UserEntity user);
  LoginResponseDto toLoginResponseDto(UserEntity user, String accessToken);
  SignupResponseDto toSignupResponseDto(UserEntity user);
}
