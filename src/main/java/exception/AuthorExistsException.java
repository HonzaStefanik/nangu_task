package exception;

public class AuthorExistsException  extends  Exception{
  public AuthorExistsException() {
  }

  public AuthorExistsException(String message) {
    super(message);
  }
}
