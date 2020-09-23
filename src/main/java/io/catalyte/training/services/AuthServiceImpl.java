package io.catalyte.training.services;

import io.catalyte.training.entities.User;
import io.catalyte.training.exceptions.BadRequest;
import io.catalyte.training.entities.Token;
import io.catalyte.training.entities.UserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Logic to create java web token from UserInput
 */
public class AuthServiceImpl implements AuthService {

  @Autowired
  private UserService userService;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  // Create secret key
  //HMAC256(SECRET_KEY);

  @Override
  public Token login(UserInput userInput) {
    if (userInput.getEmail() == null || userInput.getPassword() == null) {
      throw new BadRequest("email and/or password required");
    }

    String email = userInput.getEmail();
    String password = userInput.getPassword();

    User user = userService.findByEmail(email);

    String userPassword = user.getPassword();

    if (!password.equals(userPassword)) {
      throw new BadRequest("password invalid");
    }

    String role = "employee";

    for (String r : user.getRoles()) {
      if (r.equals("ADMIN")) {
        role = "admin";
      }
    }

//    create jwt
//    String token = JWT.create().;

//    return new Token(token);
    return null;
  }
}
