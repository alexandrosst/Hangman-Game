package com.alexandrosst;

public class UnbalancedException extends RuntimeException {
  public UnbalancedException(String message) {
    super(message);
  }
}