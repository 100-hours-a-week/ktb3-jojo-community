package com.example.anyword.shared.exception;

/**
 * 권한 없을 때의 custom exception
 */
public class ForbiddenException extends BusinessException{
  public ForbiddenException(String message) {
    super(message, 403);
  }
}
