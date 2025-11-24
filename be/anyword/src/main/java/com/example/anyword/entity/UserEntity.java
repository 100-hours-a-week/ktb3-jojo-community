package com.example.anyword.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity implements BaseEntity<Long> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long id; //PK

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password; //해싱

  @Column(unique = true, nullable = false, length = 20)
  private String nickname;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String profileImageUrl;

  @OneToMany(mappedBy = "author")
  private List<ArticleEntity> articles = new ArrayList<>();

  @OneToOne(mappedBy = "user")
  private RefreshTokenEntity refreshToken;

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
  public UserEntity copyWith(
      String email,
      String password,
      String nickname,
      String profileImageUrl) {

    //TODO: setter 사용이 최선인가 ..
    this.setEmail(email != null ? email : this.email);
    this.setPassword(password != null ? password : this.password);
    this.setNickname(nickname != null ? nickname : this.nickname);
    this. setProfileImageUrl(profileImageUrl != null ? profileImageUrl : this.profileImageUrl);

    return this;
  }


}
