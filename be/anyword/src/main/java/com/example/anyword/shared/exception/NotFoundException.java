package com.example.anyword.shared.exception;

/**
 * 404 not found custon exception
 */
public class NotFoundException extends BusinessException {
  public NotFoundException(String message) {
    super(message, 404);
  }
}
