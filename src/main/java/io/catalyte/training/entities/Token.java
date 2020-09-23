package io.catalyte.training.entities;

/**
 * Token provided by AuthServiceImpl that should contain the java web token with the header, content
 * type, and payload (email, encrypted password).
 */
public class Token {

  private String token;

  public Token(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
