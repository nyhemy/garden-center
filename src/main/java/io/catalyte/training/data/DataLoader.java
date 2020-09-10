package io.catalyte.training.data;

import io.catalyte.training.entities.Address;
import io.catalyte.training.entities.Customer;
import io.catalyte.training.entities.User;
import io.catalyte.training.repositories.AddressRepository;
import io.catalyte.training.repositories.CustomerRepository;
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
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private AddressRepository addressRepository;

  private User user1;
  private User user2;
  private User user3;

  private Address address1;
  private Address address2;
  private Address address3;

  private Customer customer1;
  private Customer customer2;
  private Customer customer3;

  @Override
  public void run(String... args) throws Exception {
    logger.info("Loading data...");

    loadUsers();
    loadAddresses();
    loadCustomers();
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

  private void loadAddresses() {
    address1 = addressRepository.save(new Address("Daniel Rd", "Shrewsbury", "MA", "01545"));
    address2 = addressRepository.save(new Address("Lynview Dr", "San Jose", "Ca", "95135"));
    address3 = addressRepository.save(new Address("Fake St", "Aiea", "HI", "96701"));
  }

  private void loadCustomers() {
    customer1 = customerRepository.save(new Customer("Jacob Keyes", "jkeyes@gmail.com", address1));
    customer2 = customerRepository.save(new Customer("Super Bee", "sbee@gmail.com", address2));
    customer3 = customerRepository.save(new Customer("Cest Tous", "ctous@gmail.com", address3));
  }
}
