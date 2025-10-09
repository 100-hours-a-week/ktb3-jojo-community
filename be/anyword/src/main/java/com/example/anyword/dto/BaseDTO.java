package com.example.anyword.dto;

public interface BaseDTO<T> {

  //ID를 가져오는 메서드를 반드시 구현해야 함
  T getId();

  //ID를 설정하는 메서드를 반드시 구현해야 함
  void setId(T id);
}
