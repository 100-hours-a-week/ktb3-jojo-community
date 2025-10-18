package com.example.anyword.service;

import static com.example.anyword.shared.constants.ResponseMessage.EMAIL_DUPLICATE;
import static com.example.anyword.shared.constants.ResponseMessage.FAIL;
import static com.example.anyword.shared.constants.ResponseMessage.NICKNAME_DUPLICATE;
import static com.example.anyword.shared.constants.ResponseMessage.SESSION_EXPIRED;
import static com.example.anyword.shared.constants.ResponseMessage.UNAUTHORIZED;
import static com.example.anyword.shared.constants.ResponseMessage.USER_NOT_FOUND;

import com.example.anyword.dto.article.AuthorInfo;
import com.example.anyword.dto.user.request.PutUserRequestDto;
import com.example.anyword.dto.user.request.SignupRequestDto;
import com.example.anyword.dto.user.request.LoginRequestDto;
import com.example.anyword.dto.user.response.UserResponseDto;
import com.example.anyword.dto.user.response.SignupResponseDto;
import com.example.anyword.entity.UserEntity;
import com.example.anyword.mapper.UserMapper;
import com.example.anyword.repository.user.UserRepository;
import com.example.anyword.shared.constants.Key;
import com.example.anyword.shared.exception.BadRequestException;
import com.example.anyword.shared.exception.ConflictException;
import com.example.anyword.shared.exception.SessionExpiredException;
import com.example.anyword.shared.exception.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  /**
   * 회원가입 로직 - service layer 에서 비즈니스 로직 검증
   */
  @Transactional
  public SignupResponseDto signup(SignupRequestDto dto){ //dto 로 변경 ...?
    if (userRepository.isEmailExist(dto.getEmail())){
      throw new ConflictException(EMAIL_DUPLICATE);
    }

    if (userRepository.isNicknameExist(dto.getNickname())){
      throw new ConflictException(NICKNAME_DUPLICATE);
    }

    //TODO: 비밀번호 해쉬 후 넘기기
    UserEntity user = new UserEntity(dto.getEmail(), dto.getPassword(), dto.getNickname(), dto.getProfileImageUrl());

    return userMapper.toSignupResponseDto(user);
  }


  public UserResponseDto login(LoginRequestDto dto){
    UserEntity foundUser = userRepository.findByEmail(dto.getEmail()).orElseThrow(()->
        new BadRequestException(USER_NOT_FOUND));

    //TODO: verifyPassword entity 에서 이동
    if (!dto.verifyPassword(foundUser.getPassword())){
      throw new BadRequestException(USER_NOT_FOUND);
    }

    return userMapper.toUserResponseDto(foundUser);
  }

  public Long getUserIdFromSession(HttpSession session){
    return Optional.ofNullable((Long) session.getAttribute(Key.SESSION_USER_ID)).orElseThrow(()->
        new UnauthorizedException(UNAUTHORIZED));
  }

  private UserEntity getUserFromSession(HttpSession session){
    Long userId = this.getUserIdFromSession(session);

    return userRepository.findById(userId).orElseThrow(()->
        new SessionExpiredException(SESSION_EXPIRED));
  }

  public UserResponseDto getCurrentUser(HttpSession session){
    UserEntity current = this.getUserFromSession(session);

    return userMapper.toUserResponseDto(current);
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
  public UserResponseDto putUser(HttpSession session, PutUserRequestDto request){
    UserEntity original = this.getUserFromSession(session);

    String newEmail = merge(request.getEmail(), original.getEmail());
    String newNickname = merge(request.getNickname(), original.getNickname());
    String newProfile = merge(request.getProfileImageUrl(), original.getProfileImageUrl());
    String newPassword = merge(request.getPassword(), original.getPassword());
    if (!Objects.requireNonNull(newEmail).equals(original.getEmail()) && userRepository.isEmailExist(newEmail)) {
      throw new ConflictException(EMAIL_DUPLICATE);
    }

    if (!Objects.requireNonNull(newNickname).equals(original.getNickname()) && userRepository.isNicknameExist(newNickname)) {
      throw new ConflictException(NICKNAME_DUPLICATE);
    }


    UserEntity updated = UserEntity.copyWith(original, newEmail, newPassword, newNickname, newProfile);

    return userMapper.toUserResponseDto(userRepository.save(updated));
  }


  @Transactional
  public void signout(HttpSession session){
    Long userId = this.getUserIdFromSession(session);

    if (!userRepository.deleteById(userId)){
      throw new BadRequestException(FAIL);
    }

    session.invalidate();
  }

  public AuthorInfo UserIdToAuthorInfo(Long userId){
    UserEntity author = userRepository.findById(userId)
        .orElse(null); //탈퇴한 회원인 경우 exception 이 발생하면 안됨
    if (author == null){
      return new AuthorInfo(0L, "탈퇴한 회원", ""); //TODO: 탈퇴한 회원 정보 상수화
    }
    return new AuthorInfo(author.getId(), author.getNickname(), author.getProfileImageUrl());
  }

}
