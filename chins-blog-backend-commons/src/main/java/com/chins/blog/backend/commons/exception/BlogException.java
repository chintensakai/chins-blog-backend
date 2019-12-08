package com.chins.blog.backend.commons.exception;

public class BlogException extends RuntimeException {

  public BlogException(String message) {
    super(message);
  }

  public BlogException(String message, Throwable cause) {
    super(message, cause);
  }

  public BlogException(Throwable cause) {
    super(cause);
  }

}
