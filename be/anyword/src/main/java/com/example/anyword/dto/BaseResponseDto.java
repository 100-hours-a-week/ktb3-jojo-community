package com.example.anyword.dto;

public class BaseResponseDto<T> {
  private final String message;
  private final T data;

  public BaseResponseDto(String message, T data){
    this.message = message;
    this.data = data;
  }


  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }
}
