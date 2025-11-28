package com.example.anyword.shared.exception;

import com.example.anyword.shared.dto.BaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  //@Valid 검증 실패 - dto 검증 실패
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BaseResponseDto<?>> handleValidationException(MethodArgumentNotValidException ex) {
    //첫번째 에러 메시지 반환
    String errorMessage = ex.getBindingResult()
        .getAllErrors()
        .get(0)
        .getDefaultMessage();

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new BaseResponseDto<>(errorMessage));
  }

  //service
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<BaseResponseDto<?>> handleBusinessException(BusinessException ex) {
    return ResponseEntity
        .status(ex.getStatusCode())
        .body(new BaseResponseDto<>(ex.getMessage()));
  }

  //기타
  @ExceptionHandler(Exception.class)
  public ResponseEntity<BaseResponseDto<?>> handleException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new BaseResponseDto<>(ex.getMessage()));
  }
}