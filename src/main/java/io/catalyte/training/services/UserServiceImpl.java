package io.catalyte.training.services;

import io.catalyte.training.entities.User;
import io.catalyte.training.exceptions.BadDataResponse;
import io.catalyte.training.exceptions.Conflict;
import io.catalyte.training.exceptions.ResourceNotFound;
import io.catalyte.training.exceptions.ServiceUnavailable;
import io.catalyte.training.repositories.UserRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * service for User
 * implements UserService interface
 */
@Service
public class UserServiceImpl implements UserService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private UserRepository userRepository;

  String[] validRoles = {"EMPLOYEE", "ADMIN"};

  /**
   * Get User by id
   *
   * @param id is the id the will be used to retrieve a User
   * @return User with provided id
   */
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

  /**
   * Queries all Users and filters through an optional query
   * Will return all Users if no query is provided
   *
   * @param user is the optional Query that will be used to filter Users
   * @return a list of Users
   */
  @Override
  public List<User> queryUsers(User user) {
    try {
      if (user.isEmpty()) {
        return userRepository.findAll();
      } else {
        Example<User> userExample = Example.of(user);
        return userRepository.findAll(userExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Adds a new User to the database
   *
   * Note: Be sure to delete User id before posting to Postman
   *
   * @param user is the User to be added
   * @return added User
   */
  @Override
  public User addUser(User user) {
    for (String role : validRoles) {
      for (String r : user.getRoles()) {
        if (r.equals(role)) {
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
      }
    }
    throw new BadDataResponse("Invalid role");
  }

  /**
   * Updates a User based off a provided id
   *
   * @param id is used to lookup the User to be updated
   * @param user is the updated User information
   * @return updated User
   */
  @Override
  public User updateUserById(Long id, User user) {
    if (!user.getId().equals(id)) {
      throw new BadDataResponse("User ID must match the ID specified in the URL");
    }

    for (String role : user.getRoles()) {
      if (!ValidRoles.validRolesList.contains(role)) {
        throw new BadDataResponse("Invalid role");
      }
    }

    for (User userEmailCheck : userRepository.findAll()) {
      if (!userEmailCheck.getId().equals(id) && userEmailCheck.getEmail()
          .equals(user.getEmail())) {

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

  /**
   * Deletes a User based off of provided id
   *
   * @param id is used to find which User to delete
   */
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
