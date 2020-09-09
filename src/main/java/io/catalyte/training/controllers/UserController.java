package io.catalyte.training.controllers;

import io.catalyte.training.entities.User;
import io.catalyte.training.services.UserService;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired private UserService userService;

  @GetMapping(value = "/{id}")
  public ResponseEntity<User> getVehicle(@PathVariable Long id) {
    logger.info(new Date() + " Get by id " + id + " request received");

    return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
  }

    @GetMapping
  public ResponseEntity<List<User>> queryUsers(User user) {
    logger.info(new Date() + " Query request received: " + user.toString());
     return new ResponseEntity<>(userService.queryUsers(user), HttpStatus.OK);
  }

}
