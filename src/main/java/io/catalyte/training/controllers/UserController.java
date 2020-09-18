package io.catalyte.training.controllers;

import io.catalyte.training.entities.User;
import io.catalyte.training.services.UserService;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * crud functionality for User entity
 */
@RestController
@RequestMapping("/users")
public class UserController {

  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  /**
   * Gets a User based off of the provided id
   * @param id of the User to be returned
   * @return User with provided id
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<User> getUser(@PathVariable Long id) {
    logger.info(new Date() + " Get by id " + id + " request received");

    return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
  }

  /**
   * Queries all Users, returning them all or filtering through a provided query
   * @param user is the object that will provide the query to be made
   * @return a list of Users
   */
  @GetMapping
  public ResponseEntity<List<User>> queryUsers(User user) {
    logger.info(new Date() + " Query request received: " + user.toString());

    return new ResponseEntity<>(userService.queryUsers(user), HttpStatus.OK);
  }

  /**
   * Saves a new User entity to the database if its fields are filled in properly
   * Ids of new Users are filled in automatically.
   * @param user is the Product to be added.
   * @return Product entity
   */
  @PostMapping
  public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
    logger.info(new Date() + " Post request received");

    return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
  }

  /**
   * Gets a User based off of the id and updates it with a new User entity
   * @param id of the User to be updated
   * @param user is the updated User
   * @return the updated User
   */
  @PutMapping(value = "/{id}")
  public ResponseEntity<User> updateUserById(
      @PathVariable Long id, @Valid @RequestBody User user) {
    logger.info(new Date() + " Put request received for id: " + id);

    return new ResponseEntity<>(userService.updateUserById(id, user), HttpStatus.OK);
  }

  /**
   * Gets a User based off of the id and deletes it
   * @param id of the User to be deleted
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity deleteUserById(@PathVariable Long id) {
    logger.info(new Date() + " Delete request received for id: " + id);

    userService.deleteUserById(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
