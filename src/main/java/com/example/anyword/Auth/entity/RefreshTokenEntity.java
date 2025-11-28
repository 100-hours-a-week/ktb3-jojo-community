package com.example.anyword.Auth.entity;

import static com.example.anyword.shared.constants.ValidErrorMessage.EMPTY_TOKEN;
import static com.example.anyword.shared.constants.ValidErrorMessage.INVALID_EXPIRED_TIME;

import com.example.anyword.User.entity.UserEntity;
import com.example.anyword.shared.exception.BadRequestException;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenEntity {

  @Id @Column(unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name="user_id", nullable = false)
  private UserEntity user;

  @Column(nullable = false)
  private String tokenHash; //TODO: 실제 토큰 문자열 그대로 저장하지 말고, hash 값 저장


  @Column(nullable = false)
  private Boolean revoked; //soft_delete 용도

  @Column(nullable = false)
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime expiresAt;

  @Column(nullable = false)
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime createdAt;

  @Column(nullable = false)
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime updatedAt;

  public RefreshTokenEntity(String tokenHash, UserEntity user, LocalDateTime expiresAt){
    this.tokenHash = tokenHash;
    this.user = user;
    this.revoked = false;
    this.expiresAt = expiresAt;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  private void updateTokenHash(String tokenHash) {
    if (tokenHash == null || tokenHash.isBlank()) {
      throw new BadRequestException(INVALID_EXPIRED_TIME);
    }
    this.tokenHash = tokenHash;
  }

  private void updateExpiresAt(LocalDateTime expiresAt) {
    if (expiresAt == null || expiresAt.isBefore(LocalDateTime.now())) {
      throw new BadRequestException(EMPTY_TOKEN);
    }
    this.expiresAt = expiresAt;
  }

  private void touchUpdatedAt() {
    this.updatedAt = LocalDateTime.now();
  }



  public void updateToken(String tokenHash, LocalDateTime expiresAt){
    this.updateTokenHash(tokenHash);
    this.updateExpiresAt(expiresAt);
    this.touchUpdatedAt();
  }


  public void logout(){
    this.revoked = true;
  }




}
