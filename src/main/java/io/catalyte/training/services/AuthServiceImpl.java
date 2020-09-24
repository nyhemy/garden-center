package io.catalyte.training.services;

import io.catalyte.training.entities.User;
import io.catalyte.training.exceptions.BadRequest;
import io.catalyte.training.entities.Token;
import io.catalyte.training.entities.UserInput;
import io.catalyte.training.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Logic to create java web token from UserInput
 */
public class AuthServiceImpl implements AuthService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  // Create secret key to use in encryption
  //HMAC256(SECRET_KEY);

  @Override
  public Token login(UserInput userInput) {

    // Checks to see if email and password from UserInput are provided, if not return a bad request
    if (userInput.getEmail() == null || userInput.getPassword() == null) {
      throw new BadRequest("email and/or password required");
    }

    // Set UserInput email and password as variables
    String email = userInput.getEmail();
    String password = userInput.getPassword();

    // Find the user in the database via the email provided and assign it to a variable
    User user = userRepository.findByEmail(email);

    // Assign password from user to a variable
    String userPassword = user.getPassword();

    //check to see if the login password is the same as the password for the user in the database
    if (!password.equals(userPassword)) {
      throw new BadRequest("password invalid");
    }

    // Check the user roles, if they're an admin assign them with admin privileges
    String role = "employee";

    for (String r : user.getRoles()) {
      if (r.equals("ADMIN")) {
        role = "admin";
      }
    }

    // create a jwt containing all the information above, and return it

//    create jwt
//    String token = JWT.create().;

//    return new Token(token);
    return null;
  }
}
