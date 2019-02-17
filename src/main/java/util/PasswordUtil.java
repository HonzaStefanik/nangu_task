package util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {

  public static String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt(12));
  }

  public static boolean isPasswordCorrect(String inputPassword, String encryptedPassword) {
    return BCrypt.checkpw(inputPassword, encryptedPassword);
  }
}
