package io.catalyte.training.services;

import io.catalyte.training.entities.User;
import io.catalyte.training.exceptions.ResourceNotFound;
import io.catalyte.training.exceptions.ServiceUnavailable;
import io.catalyte.training.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired private UserRepository userRepository;

  @Override
  public User getUser(Long id) {
    try {
      User userLookupResult = userRepository.findById(id).orElse(null);
      if (userLookupResult != null) {
        return userLookupResult;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the review
    throw new ResourceNotFound("Could not locate a user with the id: " + id);


  }

  @Override
  public List<User> queryUsers(User user) {
    try {
      if (user.isEmpty()) {
        return userRepository.findAll();
      } else {
        Example<User> vehicleExample = Example.of(user);
        return userRepository.findAll(vehicleExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }
}
