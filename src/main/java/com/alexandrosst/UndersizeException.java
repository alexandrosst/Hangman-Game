package com.alexandrosst;

public class UndersizeException extends RuntimeException {
  public UndersizeException(String message) {
    super(message);
  }
}