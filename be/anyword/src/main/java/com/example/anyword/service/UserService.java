package com.example.anyword.service;

import com.example.anyword.entity.UserEntity;
import com.example.anyword.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  /**
   * 회원가입
   * @param user
   * @return UserDTO
   */
  public UserEntity createUser(UserEntity user){
    return this.userRepository.save(user);
  }


  public UserEntity getUser(Long id){
    return userRepository.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
    );
  }
}
