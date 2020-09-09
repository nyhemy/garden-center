package io.catalyte.training.services;

import io.catalyte.training.entities.User;
import java.util.List;

public interface UserService {

  User getUser(Long id);

  List<User> queryUsers(User user);

}
