package io.catalyte.training.data;

import io.catalyte.training.entities.User;
import io.catalyte.training.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

  private final Logger logger = LoggerFactory.getLogger(DataLoader.class);

  @Autowired
  private UserRepository userRepository;

  private User user1;
  private User user2;
  private User user3;

  @Override
  public void run(String... args) throws Exception {
    logger.info("Loading data...");

    loadUsers();
  }

  private void loadUsers() {
    user1 = userRepository.save(
        new User("John Smith", "Smithy in Chief", new String[]{"Supervisor", "Smithy"}, "jsmith@gmail.com",
            "mcclangers"));
    user2 = userRepository.save(
        new User("Jinky Jane", "Miner in Chief", new String[]{"Supervisor", "Miner"}, "jjane@gmail.com",
            "saltycookies"));
    user3 = userRepository.save(
        new User("Fredo Mann", "Miner", new String[]{"Miner"}, "fmann@gmail.com",
            "diggydiggyhole"));
  }
}
