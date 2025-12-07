package com.example.anyword.User.entity;

import static com.example.anyword.shared.constants.ValidErrorMessage.NICKNAME_TOO_LONG;

import com.example.anyword.Article.entity.ArticleEntity;
import com.example.anyword.Auth.entity.RefreshTokenEntity;
import com.example.anyword.shared.exception.BadRequestException;
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


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
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

  private void updateEmailIfProvided(String email) {
    if (email == null || email.isEmpty()) {
      return; // PATCH → null이면 변경 안 함
    }
    // TODO: 이메일 형식 검증 등 추가 가능
    this.email = email;
  }

  private void updatePasswordIfProvided(String password) {
    if (password == null || password.isEmpty()) {
      return;
    }

    this.password = password;
  }

  private void updateNicknameIfProvided(String nickname) {
    if (nickname == null || nickname.isEmpty()) {
      return;
    }

    if (nickname.length() > 10){
      throw new BadRequestException(NICKNAME_TOO_LONG);
    }

    this.nickname = nickname;
  }

  private void updateProfileImageUrlIfProvided(String profileImageUrl) {
    if (profileImageUrl == null || profileImageUrl.isEmpty()) {
      return;
    }
    this.profileImageUrl = profileImageUrl;
  }

  // setter 사용 x, 의미 있는 메서드
  public UserEntity returnUpdatedUser(
      String email,
      String password,
      String nickname,
      String profileImageUrl
  ) {
    this.updateEmailIfProvided(email);
    this.updatePasswordIfProvided(password);
    this.updateNicknameIfProvided(nickname);
    this.updateProfileImageUrlIfProvided(profileImageUrl);

    return this;
  }


}
