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

@RestController
@RequestMapping("/users")
public class UserController {

  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @GetMapping(value = "/{id}")
  public ResponseEntity<User> getUser(@PathVariable Long id) {
    logger.info(new Date() + " Get by id " + id + " request received");

    return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<User>> queryUsers(User user) {
    logger.info(new Date() + " Query request received: " + user.toString());

    return new ResponseEntity<>(userService.queryUsers(user), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
    logger.info(new Date() + " Post request received");

    return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<User> updateUserById(
      @PathVariable Long id, @Valid @RequestBody User user) {
    logger.info(new Date() + " Put request received for id: " + id);

    return new ResponseEntity<>(userService.updateUserById(id, user), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity deleteUserById(@PathVariable Long id) {
    logger.info(new Date() + " Delete request received for id: " + id);

    userService.deleteUserById(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
