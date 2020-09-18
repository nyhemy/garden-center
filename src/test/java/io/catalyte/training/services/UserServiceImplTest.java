package io.catalyte.training.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.catalyte.training.entities.User;
import io.catalyte.training.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

public class UserServiceImplTest {

  @Mock
  UserRepository userRepository;
  @InjectMocks
  UserServiceImpl userServiceImpl;

  User testUser1;
  User testUser2;
  List<User> testList = new ArrayList<User>();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    testUser1 = new User("John Smith", "Smithy in Chief", new String[]{"Supervisor", "Smithy"},
        "jsmith@gmail.com",
        "mcclangers");

    testUser2 = new User("Jinky Jane", "Miner in Chief", new String[]{"Supervisor", "Miner"},
        "jjane@gmail.com",
        "saltycookies");

    testUser1.setId(1L);
    testUser2.setId(2L);

    testList.add(testUser1);

    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(testList.get(0)));
    when(userRepository.save(any(User.class))).thenReturn(testList.get(0));
    when(userRepository.findAll()).thenReturn(testList);
    when(userRepository.findAll(any(Example.class))).thenReturn(testList);
  }

  @Test
  public void getUser() {
    User result = userServiceImpl.getUser(Long.valueOf(1));
    Assert.assertEquals(testUser1, result);
  }

  @Test
  public void queryUsers() {
    List<User> result = userServiceImpl.queryUsers(new User());

    Assert.assertEquals(testList, result);
  }

  @Test
  public void testQueryUsersExample() {
    List<User> result = userServiceImpl.queryUsers(testUser1);

    Assert.assertEquals(testList, result);
  }

  @Test
  public void addUser() {
    when(userRepository.save(any(User.class))).thenReturn(testUser2);

    User result = userServiceImpl.addUser(testUser2);
    Assert.assertEquals(testUser2, result);
  }

  @Test
  public void updateUserById() {
    User result = userServiceImpl.updateUserById(1L, testUser1);
    Assert.assertEquals(testUser1, result);
  }

  @Test
  public void deleteUserById() {
    when(userRepository.existsById(anyLong())).thenReturn(true);
    userServiceImpl.deleteUserById(Long.valueOf(1));

    verify(userRepository).deleteById(any());
  }
}