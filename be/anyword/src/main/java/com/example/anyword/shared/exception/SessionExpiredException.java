package com.example.anyword.shared.exception;

/**
 * session expired - session id 는 있는데 못찾을 때
 */

public class SessionExpiredException extends BusinessException{

  public SessionExpiredException(String message){
    super(message, 401);
  }

}
