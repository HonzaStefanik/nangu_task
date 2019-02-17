package util;

public class AuthorUtil {

  private AuthorUtil() {
  }

  public static boolean authorMatches(int headerId, int authorId) {
    return headerId == authorId;
  }

}
