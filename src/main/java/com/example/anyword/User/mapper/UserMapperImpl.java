package com.example.anyword.User.mapper;

import com.example.anyword.User.dto.response.LoginResponseDto;
import com.example.anyword.User.dto.response.SignupResponseDto;
import com.example.anyword.User.dto.response.UserResponseDto;
import com.example.anyword.User.entity.UserEntity;
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
  public LoginResponseDto toLoginResponseDto(UserEntity user, String accessToken) {
    if (user == null) return null;
    return new LoginResponseDto(
        user.getId(),
        user.getEmail(),
        user.getNickname(),
        user.getProfileImageUrl(),
        accessToken
    );
  }


  @Override
  public SignupResponseDto toSignupResponseDto(UserEntity user) {
    if (user == null) return null;
    return new SignupResponseDto(user.getId());
  }

}
