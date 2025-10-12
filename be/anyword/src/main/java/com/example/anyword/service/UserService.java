package com.example.anyword.service;

import com.example.anyword.dto.user.PutUserRequestDto;
import com.example.anyword.dto.user.SignupRequestDto;
import com.example.anyword.dto.user.LoginRequestDto;
import com.example.anyword.entity.UserEntity;
import com.example.anyword.repository.UserRepository;
import com.example.anyword.shared.constants.Key;
import com.example.anyword.shared.constants.ResponseMessage;
import com.example.anyword.shared.exception.BadRequestException;
import com.example.anyword.shared.exception.ConflictException;
import com.example.anyword.shared.exception.SessionExpiredException;
import com.example.anyword.shared.exception.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * 회원가입 로직 - service layer 에서 비즈니스 로직 검증
   */
  @Transactional
  public UserEntity signup(SignupRequestDto dto){ //dto 로 변경 ...?
    if (userRepository.isEmailExist(dto.getEmail())){
      throw new ConflictException(ResponseMessage.EMAIL_DUPLICATE);
    }

    if (userRepository.isNicknameExist(dto.getNickname())){
      throw new ConflictException(ResponseMessage.NICKNAME_DUPLICATE);
    }

    //TODO: 비밀번호 해쉬 후 넘기기
    UserEntity user = new UserEntity(dto.getEmail(), dto.getPassword(), dto.getNickname(), dto.getProfileImageUrl());

    return userRepository.save(user);
  }


  public UserEntity login(LoginRequestDto dto){
    UserEntity foundUser = userRepository.findByEmail(dto.getEmail()).orElseThrow(()->
        new BadRequestException(ResponseMessage.USER_NOT_FOUND));

    if (!dto.verifyPassword(foundUser.getPassword())){
      throw new BadRequestException(ResponseMessage.USER_NOT_FOUND);
    }

    return foundUser;
  }

  public Long getUserIdFromSession(HttpSession session){
    return Optional.ofNullable((Long) session.getAttribute(Key.SESSION_USER_ID)).orElseThrow(()->
        new UnauthorizedException(ResponseMessage.UNAUTHORIZED));
  }

  public UserEntity getUserFromSession(HttpSession session){
    Long userId = this.getUserIdFromSession(session);

    return userRepository.findById(userId).orElseThrow(()->
        new SessionExpiredException(ResponseMessage.SESSION_EXPIRED));
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
  public UserEntity putUser(HttpSession session, PutUserRequestDto request){
    UserEntity original = getUserFromSession(session);

    String newEmail = merge(request.getEmail(), original.getEmail());
    String newNickname = merge(request.getNickname(), original.getNickname());
    String newProfile = merge(request.getProfileImageUrl(), original.getProfileImageUrl());
    String newPassword = merge(request.getPassword(), original.getPassword());
    if (userRepository.isEmailExist(newEmail)){
      throw new ConflictException(ResponseMessage.EMAIL_DUPLICATE);
    }

    if (userRepository.isNicknameExist(newNickname)){
      throw new ConflictException(ResponseMessage.NICKNAME_DUPLICATE);
    }


    UserEntity updated = UserEntity.copyWith(original, newEmail, newPassword, newNickname, newProfile);

    return userRepository.save(updated);
  }


  @Transactional
  public void signout(HttpSession session){
    Long userId = this.getUserIdFromSession(session);

    if (!userRepository.deleteById(userId)){
      throw new BadRequestException(ResponseMessage.FAIL);
    }

    session.invalidate();
  }

}
