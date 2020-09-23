package io.catalyte.training.entities;

import static io.catalyte.training.constants.StringConstants.INVALID_EMAIL;
import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Email and password credentials provided by user login
 */
public class UserInput {

  @NotBlank(message = "email" + REQUIRED_FIELD)
  @Email(message = "email" + INVALID_EMAIL)
  private String email;

  @NotBlank(message = "password" + REQUIRED_FIELD)
  private String password;

  public UserInput() {
  }

  public UserInput(@NotBlank(message = "email"
      + REQUIRED_FIELD) @Email(message = "email"
      + INVALID_EMAIL) String email,
      @NotBlank(message = "password"
          + REQUIRED_FIELD) String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
