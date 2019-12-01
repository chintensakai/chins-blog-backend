package com.chins.blog.backend.commons.base;

import lombok.Data;

@Data
public class ResponseBase<T> {

  private int code;
  private String message;
  private T data;

  public ResponseBase(int code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }
}
