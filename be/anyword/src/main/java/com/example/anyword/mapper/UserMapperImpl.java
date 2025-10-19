package com.example.anyword.mapper;

import com.example.anyword.dto.user.response.SignupResponseDto;
import com.example.anyword.dto.user.response.UserResponseDto;
import com.example.anyword.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

  @Override
  public UserResponseDto toUserResponseDto(UserEntity user) {
    if (user == null) return null;
    return new UserResponseDto(
        user.getId(),
        user.getEmail(),
        user.getNickname(),
        user.getProfileImageUrl()
    );
  }

  @Override
  public SignupResponseDto toSignupResponseDto(UserEntity user) {
    if (user == null) return null;
    return new SignupResponseDto(user.getId());
  }

}
