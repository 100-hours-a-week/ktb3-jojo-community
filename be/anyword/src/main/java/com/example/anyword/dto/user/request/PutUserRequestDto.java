package com.example.anyword.dto.user.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PutUserRequestDto {

  //null 허용
  @Email
  private String email;

  @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=\\[{\\]};:'\",<.>/?\\\\|`~])[A-Za-z\\d!@#$%^&*()_\\-+=\\[{\\]};:'\",<.>/?\\\\|`~]{8,20}$")
  private String password; //해싱

  @Size(max=10)
  @Pattern(regexp = "^\\S+$")
  private String nickname;

  private String profileImageUrl;

  public PutUserRequestDto() {}


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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
