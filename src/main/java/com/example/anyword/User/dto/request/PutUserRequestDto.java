package com.example.anyword.User.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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


}
