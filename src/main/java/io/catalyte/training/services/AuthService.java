package io.catalyte.training.services;

import io.catalyte.training.entities.Token;
import io.catalyte.training.entities.UserInput;

/**
 * Interface for AuthServiceImpl
 */
public interface AuthService {

  Token login(UserInput userInput);
}
