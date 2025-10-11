package com.example.anyword.shared.exception;


/**
 * 400 bad request custom exception
 */
public class BadRequestException extends BusinessException{

  public BadRequestException(String message) {
    super(message, 400);
  }

}
