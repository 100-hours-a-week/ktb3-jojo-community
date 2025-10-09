package com.example.anyword.dto;

public class UserDTO implements BaseDTO<Long> {
  private Long id; //PK

  private String email;
  private String password; //해싱
  private String nickname;
  private String profileImageUrl;

  // 데이터 저장을 위한 생성자 (ID 제외)
  public UserDTO(String email, String password, String nickname, String profileImageUrl) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.profileImageUrl = profileImageUrl;
  }

  @Override
  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getNickname() {
    return nickname;
  }

  public String getPassword() {
    return password;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }


  @Override
  public void setId(Long userId) {
    this.id = userId;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }


}
