package com.example.anyword.mapper;

import com.example.anyword.dto.user.response.UserResponseDto;
import com.example.anyword.dto.user.response.SignupResponseDto;
import com.example.anyword.entity.UserEntity;

public interface UserMapper {
  UserResponseDto toUserResponseDto(UserEntity user);
  SignupResponseDto toSignupResponseDto(UserEntity user);
}
