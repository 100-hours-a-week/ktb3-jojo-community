package com.example.anyword.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LoginRequestDto {
  @NotBlank
  @Email
  private final String email;

  @NotBlank
  @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=\\[{\\]};:'\",<.>/?\\\\|`~])[A-Za-z\\d!@#$%^&*()_\\-+=\\[{\\]};:'\",<.>/?\\\\|`~]{8,20}$")
  private final String password;


  public LoginRequestDto(String email, String password){
    this.email = email;
    this.password = password;
  }


  public String getEmail() {
    return email;
  }

  public String getPassword(){
    return password;
  }

  public Boolean verifyPassword(String password){
    return this.password.equals(password);
  }
}
