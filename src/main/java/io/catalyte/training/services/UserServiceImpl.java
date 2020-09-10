package io.catalyte.training.services;

import static io.catalyte.training.constants.StringConstants.INPUT_CONFLICT;
import static io.catalyte.training.constants.StringConstants.NOT_FOUND;

import io.catalyte.training.entities.User;
import io.catalyte.training.exceptions.BadDataResponse;
import io.catalyte.training.exceptions.Conflict;
import io.catalyte.training.exceptions.ExceptionResponse;
import io.catalyte.training.exceptions.ResourceNotFound;
import io.catalyte.training.exceptions.ServiceUnavailable;
import io.catalyte.training.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private UserRepository userRepository;

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

  @Override
  public User addUser(User user) {
    for (User userEmailCheck : userRepository.findAll()) {
      if (userEmailCheck.getEmail().equals(user.getEmail())) {

        throw new Conflict("Email is already taken by another user");
      }
    }
    try {
      return userRepository.save(user);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public User updateUserById(Long id, User user) {
    if (!user.getId().equals(id)) {
      throw new BadDataResponse("User ID must match the ID specified in the URL");
    }

    for (User userEmailCheck : userRepository.findAll()) {

      if (!userEmailCheck.getId().equals(id) && userEmailCheck.getEmail().equals(user.getEmail())) {

        throw new Conflict("Email is already taken by another user");
      }
    }

    try {
      User userFromDb = userRepository.findById(id).orElse(null);

      if (userFromDb != null) {
        return userRepository.save(user);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound("Could not locate a user with the id: " + id);
  }

  @Override
  public void deleteUserById(Long id) {
    try {
      if (userRepository.existsById(id)) {
        userRepository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound("Could not locate a user with the id: " + id);
  }
}
