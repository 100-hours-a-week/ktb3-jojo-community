package com.example.anyword.shared.exception;

/**
 * 서버와 리소스 충돌 시 custom exception
 */
public class ConflictException extends BusinessException{

  public ConflictException(String message){
    super(message, 409);
  }

}
