package io.catalyte.training.services;

import io.catalyte.training.entities.User;
import java.util.List;

public interface UserService {

  User getUser(Long id);

  List<User> queryUsers(User user);

  User addUser(User user);

  User updateUserById(Long id, User user);

  void deleteUserById(Long id);

}
