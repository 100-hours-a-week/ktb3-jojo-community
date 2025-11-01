package com.example.anyword.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity implements BaseEntity<Long> {
  @Id @Setter
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
  @Column(unique = true, nullable = false)
  private Long id; //PK

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password; //해싱

  @Column(unique = true, nullable = false, length = 20)
  private String nickname;

  @Column(nullable = false, length = 512)
  private String profileImageUrl;

  @OneToMany(mappedBy = "author")
  private List<ArticleEntity> articles = new ArrayList<>();

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

  //TODO: 삭제하기
  /**편의 메서드 for 동기화*/
  public void addArticle(ArticleEntity article){
    this.articles.add(article);
    article.setAuthor(this);
  }

  public void removeArticle(ArticleEntity article) {
    this.articles.remove(article);
    article.setAuthor(null);
  }

}
