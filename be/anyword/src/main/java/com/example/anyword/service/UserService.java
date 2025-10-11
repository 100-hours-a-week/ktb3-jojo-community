package com.example.anyword.service;

import com.example.anyword.dto.user.CreateUserDto;
import com.example.anyword.dto.user.LoginRequestDto;
import com.example.anyword.entity.UserEntity;
import com.example.anyword.repository.UserRepository;
import com.example.anyword.shared.constants.ResponseMessage;
import com.example.anyword.shared.exception.BadRequestException;
import com.example.anyword.shared.exception.ConflictException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * 회원가입 로직 - service layer 에서 비즈니스 로직 검증
   */
  public UserEntity signup(CreateUserDto dto){ //dto 로 변경 ...?
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

}
