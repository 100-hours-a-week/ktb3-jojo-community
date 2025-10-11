package com.example.anyword.shared.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  //@Valid 검증 실패 - dto 검증 실패
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
    //첫번째 에러 메시지 반환
    String errorMessage = ex.getBindingResult()
        .getAllErrors()
        .get(0)
        .getDefaultMessage();

    return ResponseEntity.status(400).body(errorMessage);
  }

  //service
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<?> handleBusinessException(BusinessException ex) {
    return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
  }

  //기타
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception ex) {
    return ResponseEntity.status(500).body(ex); //TODO: error message 정확히
  }
}