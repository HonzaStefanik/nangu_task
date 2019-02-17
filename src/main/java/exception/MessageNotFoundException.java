package exception;

public class MessageNotFoundException extends Exception {
  public MessageNotFoundException() {
    super();
  }

  public MessageNotFoundException(String message) {
    super(message);
  }
}
