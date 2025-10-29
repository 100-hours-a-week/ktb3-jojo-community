package com.example.anyword.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
public class UserEntity implements BaseEntity<Long> {
  @Setter
  @Id
  @GeneratedValue
  private Long id; //PK

  private final String email;

  private final String password; //해싱

  private final String nickname;

  private final String profileImageUrl;

  // 데이터 저장을 위한 생성자 (ID 제외)
  public UserEntity(String email, String password, String nickname, String profileImageUrl) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.profileImageUrl = profileImageUrl;
  }

  private UserEntity(Long id, String email, String password, String nickname, String profileImageUrl) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.profileImageUrl = profileImageUrl;
  }

  //setter 사용 x, patch
  public static UserEntity copyWith(
      UserEntity original,
      String email,
      String password,
      String nickname,
      String profileImageUrl) {

    return new UserEntity(
        original.getId(),
        email != null ? email : original.email,
        password != null ? password : original.password,
        nickname != null ? nickname : original.nickname,
        profileImageUrl != null ? profileImageUrl : original.profileImageUrl
    );
  }

}
