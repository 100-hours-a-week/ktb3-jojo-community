package com.example.anyword.dto.user;

public class CreateUserDto {
  private final String email;
  private final String password; //해싱
  private final String nickname;
  private final String profileImageUrl;

  public CreateUserDto(String email, String password, String nickname, String profileImageUrl) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.profileImageUrl = profileImageUrl;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getNickname() {
    return nickname;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }
}
