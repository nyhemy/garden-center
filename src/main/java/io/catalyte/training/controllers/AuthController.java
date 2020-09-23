package io.catalyte.training.controllers;

import io.catalyte.training.services.AuthService;
import io.catalyte.training.entities.Token;
import io.catalyte.training.entities.UserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/authenticate")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping
  Token login(@RequestBody UserInput userInput) {
    return authService.login(userInput);
  }
}
