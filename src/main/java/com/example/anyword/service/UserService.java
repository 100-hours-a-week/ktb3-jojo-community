package com.example.anyword.service;

import static com.example.anyword.shared.constants.Key.SESSION_USER_ID;
import static com.example.anyword.shared.constants.ResponseMessage.EMAIL_DUPLICATE;
import static com.example.anyword.shared.constants.ResponseMessage.NICKNAME_DUPLICATE;
import static com.example.anyword.shared.constants.ResponseMessage.SESSION_EXPIRED;
import static com.example.anyword.shared.constants.ResponseMessage.USER_NOT_FOUND;

import com.example.anyword.dto.article.AuthorInfoDto;
import com.example.anyword.dto.user.request.PutUserRequestDto;
import com.example.anyword.dto.user.request.SignupRequestDto;
import com.example.anyword.dto.user.request.LoginRequestDto;
import com.example.anyword.dto.user.response.LoginResponseDto;
import com.example.anyword.dto.user.response.UserResponseDto;
import com.example.anyword.dto.user.response.SignupResponseDto;
import com.example.anyword.entity.RefreshTokenEntity;
import com.example.anyword.entity.UserEntity;
import com.example.anyword.mapper.UserMapper;
import com.example.anyword.repository.auth.RefreshTokenRepository;
import com.example.anyword.repository.user.UserRepository;
import com.example.anyword.security.JWTUtil;
import com.example.anyword.shared.exception.BadRequestException;
import com.example.anyword.shared.exception.ConflictException;
import com.example.anyword.shared.exception.SessionExpiredException;
import com.example.anyword.shared.utils.ValidateUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  //jwt 추가
  private final JWTUtil jwtUtil;
  private final RefreshTokenRepository refreshTokenRepository;

  public UserService(UserRepository userRepository, UserMapper userMapper, JWTUtil jwtUtil,
      RefreshTokenRepository refreshTokenRepository) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.jwtUtil = jwtUtil;
    this.refreshTokenRepository = refreshTokenRepository;
  }

  /**
   * 회원가입 로직 - service layer 에서 비즈니스 로직 검증
   */
  @Transactional
  public SignupResponseDto signup(SignupRequestDto dto){ //dto 로 변경 ...?
    if (userRepository.existsByEmail(dto.getEmail())){
      throw new ConflictException(EMAIL_DUPLICATE);
    }

    if (userRepository.existsByNickname(dto.getNickname())){
      throw new ConflictException(NICKNAME_DUPLICATE);
    }

    //TODO: 비밀번호 해쉬 후 넘기기
    UserEntity saved = userRepository.save(new UserEntity(
        dto.getEmail(), dto.getPassword(), dto.getNickname(), dto.getProfileImageUrl()));

    return userMapper.toSignupResponseDto(saved);
  }

  private boolean verifyPassword(String dtoPassword, String password){
    return dtoPassword.equals(password);
  }



  public void saveRefreshTokenToCookie(String refreshToken, HttpServletResponse response){
    try{
    Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
    refreshTokenCookie.setHttpOnly(true);

    response.addCookie(refreshTokenCookie);} catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private HashMap<String, String> createToken(UserEntity user){
    String refreshToken = jwtUtil.createRefreshToken(user.getEmail());
    String accessToken = jwtUtil.createRefreshToken(user.getEmail());

    //Date -> LocalDateTime (Date - 레거시 but jjwt 때문에)
    LocalDateTime expireAt = jwtUtil.getExpirationTime(refreshToken).toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime();

    //기존
    RefreshTokenEntity preToken = refreshTokenRepository.findByUserId(user.getId()).orElse(null);

    if (preToken == null){
      //첫로그인
      RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(refreshToken, user, expireAt);
      refreshTokenRepository.save(refreshTokenEntity);

    }else{
      //기존 로그인 덮어씌우기 (user 당 로그인 한번만 허용)
      preToken.updateToken(refreshToken, expireAt);
    }


    HashMap<String, String> returnValue = new HashMap<>();

    returnValue.put("at", accessToken);
    returnValue.put("rt", refreshToken);

    return returnValue;
  }




  @Transactional
  public LoginResponseDto login(LoginRequestDto dto, HttpServletResponse response){
    UserEntity foundUser = userRepository.findByEmail(dto.getEmail()).orElseThrow(()->
        new BadRequestException(USER_NOT_FOUND));

    if (!this.verifyPassword(dto.getPassword(), foundUser.getPassword())){
      throw new BadRequestException(USER_NOT_FOUND);
    }

    HashMap<String, String> Tokens = createToken(foundUser);
    saveRefreshTokenToCookie(Tokens.get("rt"), response);


    return userMapper.toLoginResponseDto(foundUser, Tokens.get("at"));
  }

  public void logout(UserEntity user){
    user.getRefreshToken().logout();
  }

  private UserEntity getUserFromSession(HttpSession session){
    Long userId = (Long) session.getAttribute(SESSION_USER_ID);

    return userRepository.findById(userId).orElseThrow(()->
        new SessionExpiredException(SESSION_EXPIRED));
  }

  public UserResponseDto getCurrentUser(UserEntity user){
    return userMapper.toUserResponseDto(user);
  }

  /**
   * 빈 문자열과 null을 구분하기 위한 함수
   * @param incoming - param 값
   * @param original - 원본 값
   * @return 빈 문자열이면 null을, null이면 original 값 반환
   */
  private String merge(String incoming, String original) {
    if (incoming == null) return original;
    if (incoming.isBlank()) return null;
    return incoming.trim();
  }


  @Transactional
  public UserResponseDto putUser(UserEntity original, PutUserRequestDto request){

    String newEmail = merge(request.getEmail(), original.getEmail());
    String newNickname = merge(request.getNickname(), original.getNickname());
    String newProfile = merge(request.getProfileImageUrl(), original.getProfileImageUrl());
    String newPassword = merge(request.getPassword(), original.getPassword());

    ValidateUtil.validateDuplicateField(newEmail, original.getEmail(), userRepository::existsByEmail, EMAIL_DUPLICATE);
    ValidateUtil.validateDuplicateField(newNickname, original.getNickname(), userRepository::existsByNickname, NICKNAME_DUPLICATE);

    UserEntity updated = original.returnUpdatedUser(newEmail, newPassword, newNickname, newProfile);
    userRepository.save(updated);


    return userMapper.toUserResponseDto(updated);
  }


  @Transactional
  public void signout(UserEntity user){
    Long userId = user.getId();
    userRepository.deleteById(userId);
  }

  @Transactional(readOnly = true)
  public AuthorInfoDto UserIdToAuthorInfo(Long userId){
    UserEntity author = userRepository.findById(userId)
        .orElse(null); //탈퇴한 회원인 경우 exception 이 발생하면 안됨
    if (author == null){
      return new AuthorInfoDto(0L, "탈퇴한 회원", ""); //TODO: 탈퇴한 회원 정보 상수화
    }
    return new AuthorInfoDto(author.getId(), author.getNickname(), author.getProfileImageUrl());
  }

}
