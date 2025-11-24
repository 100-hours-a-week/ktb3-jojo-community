package com.example.anyword.authentication;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts
    ;

/**
 * 유저 정보로 Jwt 발급하거나 Jwt 정보로 유저 정보를 가져옴
 */
@Component
public class JWTProvider {
  private final SecretKey SECRET_KEY;
  private final Long ACCESS_EXPIRE_TIME;
  private final Long REFRESH_EXPIRE_TIME;

  public JWTProvider(@Value("${jwt.token.secret_key}") String SECRET_KEY,
      @Value("${jwt.token.access_expiration}") Long ACCESS_EXPIRE_TIME,
      @Value("${jwt.token.refresh_expiration}") Long REFRESH_EXPIRE_TIME
  ){
    this.SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    this.ACCESS_EXPIRE_TIME = ACCESS_EXPIRE_TIME;
    this.REFRESH_EXPIRE_TIME = REFRESH_EXPIRE_TIME;
  }

  public String createAccessToken(String email) {
    return createToken(email, ACCESS_EXPIRE_TIME);
  }

  public String createRefreshToken(String email) {
    return createToken(email, REFRESH_EXPIRE_TIME);
  }


  private String createToken(String email, Long expireTime) {
    long now = System.currentTimeMillis();
    Date issuedAt = new Date(now);
    Date expiration = new Date(now + expireTime);

    return Jwts.builder()
        .subject(email)
        .issuedAt(issuedAt)
        .expiration(expiration)
        .signWith(SECRET_KEY)
        .compact();
  }

  private Claims parseClaims(String token) {
    return Jwts
        .parser()
        .verifyWith(SECRET_KEY)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }


  public boolean validateToken(String token) {
    try {
      Claims claims = parseClaims(token);

      Date exp = claims.getExpiration();
      return exp != null && exp.after(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }


  public String getEmail(String token) {
    return parseClaims(token).getSubject();
  }


}
