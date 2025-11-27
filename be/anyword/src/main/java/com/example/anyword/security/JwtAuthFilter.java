package com.example.anyword.security;

import static com.example.anyword.shared.constants.ResponseMessage.ACCESSTOKEN_EXPIRED;
import static com.example.anyword.shared.constants.ResponseMessage.UNAUTHORIZED;

import com.example.anyword.shared.exception.UnauthorizedException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  private final UserDetailsService userDetailsService;
  private final JWTUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String requestUrl = request.getRequestURI();
    String requestMethod = request.getMethod();
    //인증 필요 없는 요청들
    if (requestUrl.equals("/api/user/login") && requestMethod.equals("POST") ||
        requestUrl.equals("/api/user/signup") && requestMethod.equals("POST")
    ) {
      filterChain.doFilter(request, response);
      return;
    }


    //accessToken 꺼내기
    String authHeader = request.getHeader("authorization");

    if(authHeader == null || !authHeader.startsWith("Bearer")){
      //토큰 없음 처리
      throw new UnauthorizedException(UNAUTHORIZED);
    }

    String accessToken = authHeader.replaceFirst("Bearer ", "");

    //token 유효성 검증

    if (!jwtUtil.validateToken(accessToken)){
      // response 조정
      throw new JwtException(ACCESSTOKEN_EXPIRED);
    }

    //token 에서 email 추출
    String email = jwtUtil.getEmail(accessToken);

    UserDetails userDetail = userDetailsService.loadUserByUsername(email); //username -> email 사용

    //authentication 객체 생성
    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities()); // principal - email / credentails - password
    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);

  }
}
