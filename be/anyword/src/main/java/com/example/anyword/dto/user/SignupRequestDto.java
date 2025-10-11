package com.example.anyword.dto.user;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignupRequestDto {
  @NotBlank
  @Email
  private final String email;

  @NotBlank
  @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=\\[{\\]};:'\",<.>/?\\\\|`~])[A-Za-z\\d!@#$%^&*()_\\-+=\\[{\\]};:'\",<.>/?\\\\|`~]{8,20}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
  private final String password; //해싱

  @NotBlank
  @Size(max=10)
  @Pattern(regexp = "^\\S+$")
  private final String nickname;

  @NotBlank
  private final String profileImageUrl;

  public SignupRequestDto(String email, String password, String nickname, String profileImageUrl) {
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
