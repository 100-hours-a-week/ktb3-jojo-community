package com.example.anyword.shared.exception;

/**
 * 미인증 시의 custom exception
 */
public class UnauthorizedException extends BusinessException{
  public UnauthorizedException(String message){
    super(message, 401);
  }

}
