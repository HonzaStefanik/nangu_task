package util;

import domain.entities.Author;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {


  private Base64Util(){}

  public static String encodeCredentials(String username, String password) {
    String credentials = username + ":" + password;
    return Base64.getEncoder().encodeToString(credentials.getBytes());
  }

  public static Author decodeCredentials(String authString) {
    byte[] credentialsBytes = Base64.getDecoder().decode(authString);
    String credentials = new String(credentialsBytes, StandardCharsets.UTF_8);
    String[] credentialsArray = credentials.split(":");
    String name = credentialsArray[0];
    String password = credentialsArray[1];
    return new Author(name, password);
  }
}
