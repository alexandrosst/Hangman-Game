package exceptions;

public class UnbalancedException extends RuntimeException {
  private String str;
  public UnbalancedException(String str) {
    this.str = str;
  }
  @Override
  public String toString() {
    return str;
  }
}
