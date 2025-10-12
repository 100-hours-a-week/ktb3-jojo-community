package com.example.anyword.dto.article;

public class AuthorInfo {
  private Long id;
  private String nickname;
  private String profileImageUrl;


  public AuthorInfo(Long id, String nickname, String profileImageUrl) {
    this.id = id;
    this.nickname = nickname;
    this.profileImageUrl = profileImageUrl;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }
}
