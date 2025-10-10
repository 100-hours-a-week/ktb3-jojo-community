package com.example.anyword.entity;

public interface BaseEntity<T> {

  T getId();

  void setId(T id);
}
