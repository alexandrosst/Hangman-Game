package exceptions;

public class UndersizeException extends RuntimeException {
  private String str;
  public UndersizeException(String str) {
    this.str = str;
  }
  @Override
  public String toString() {
    return str;
  }
}
