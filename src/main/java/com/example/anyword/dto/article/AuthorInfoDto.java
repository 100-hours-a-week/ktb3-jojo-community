package com.example.anyword.dto.article;

import com.example.anyword.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorInfoDto {
  private Long id;
  private String nickname;
  private String profileImageUrl;

  public AuthorInfoDto(){}


  public AuthorInfoDto(Long id, String nickname, String profileImageUrl) {
    this.id = id;
    this.nickname = nickname;
    this.profileImageUrl = profileImageUrl;
  }

  public static AuthorInfoDto from(UserEntity user){
    return new AuthorInfoDto(user.getId(), user.getNickname(),
        user.getProfileImageUrl());
  }


}
