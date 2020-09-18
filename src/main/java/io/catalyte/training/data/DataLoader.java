package io.catalyte.training.data;

import io.catalyte.training.entities.Address;
import io.catalyte.training.entities.Customer;
import io.catalyte.training.entities.Item;
import io.catalyte.training.entities.Order;
import io.catalyte.training.entities.Product;
import io.catalyte.training.entities.User;
import io.catalyte.training.repositories.AddressRepository;
import io.catalyte.training.repositories.CustomerRepository;
import io.catalyte.training.repositories.ItemRepository;
import io.catalyte.training.repositories.OrderRepository;
import io.catalyte.training.repositories.ProductRepository;
import io.catalyte.training.repositories.UserRepository;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Dataloader generates data to be put into database on startup
 */
@Component
public class DataLoader implements CommandLineRunner {

  private final Logger logger = LoggerFactory.getLogger(DataLoader.class);

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private ItemRepository itemRepository;

  private User user1;
  private User user2;
  private User user3;

  private Address address1;
  private Address address2;
  private Address address3;

  private Customer customer1;
  private Customer customer2;
  private Customer customer3;

  private Product product1;
  private Product product2;
  private Product product3;

  private Set<Item> items1 = new HashSet<>();
  private Set<Item> items2 = new HashSet<>();

  private Order order1 = new Order();
  private Order order2 = new Order();

  /**
   * Loads data in order based on functions
   *
   * @param args
   * @throws Exception
   */
  @Override
  public void run(String... args) throws Exception {
    logger.info("Loading data...");

    loadUsers();
    loadCustomers();
    loadAddresses();
    loadProducts();
    loadOrders();
  }

  private void loadUsers() {
    user1 = userRepository.save(
        new User("John Smith", "Smithy in Chief", new String[]{"ADMIN"},
            "jsmith@gmail.com",
            "mcclangers"));
    user2 = userRepository.save(
        new User("Jinky Jane", "Miner in Chief", new String[]{"ADMIN", "EMPLOYEE"},
            "jjane@gmail.com",
            "saltycookies"));
    user3 = userRepository.save(
        new User("Fredo Mann", "Miner", new String[]{"EMPLOYEE"}, "fmann@gmail.com",
            "diggydiggyhole"));
  }

  private void loadCustomers() {
    //change so that you build customers first then add addresses
    customer1 = customerRepository.save(new Customer("Jacob Keyes", "jkeyes@gmail.com"));
    customer2 = customerRepository.save(new Customer("Super Bee", "sbee@gmail.com"));
    customer3 = customerRepository.save(new Customer("Cest Tous", "ctous@gmail.com"));
  }

  private void loadAddresses() {
    address1 = addressRepository
        .save(new Address("Daniel Rd", "Shrewsbury", "MA", "01545", customer1));
    address2 = addressRepository
        .save(new Address("Lynview Dr", "San Jose", "CA", "95135", customer2));
    address3 = addressRepository.save(new Address("Fake St", "Aiea", "HI", "96701", customer3));
  }

  private void loadProducts() {
    product1 = productRepository.save(new Product("TTNM-00004-ORE", "Ore", "Titanium Ore Grade 04",
        "Grade 04 raw titanium ore, primarily used in high stress environments.",
        "Charon Industries",
        BigDecimal.valueOf(30.00)));
    product2 = productRepository
        .save(new Product("TTNM-00023-ALY", "Alloy", "Titanium Alloy Grade 23",
            "Grade 23 titanium alloy, superior choice for various rugged fabrications.",
            "Charon Industries",
            BigDecimal.valueOf(50.00)));
    product3 = productRepository
        .save(new Product("TTNM-6Al6V-ALY", "Alloy", "Titanium Alloy Grade 6Al-6V-2Sn",
            "Grade 6Al-6V-2Sn titanium alloy, maintains its stability and strength in environments with temps up to 550°C.",
            "Charon Labs",
            BigDecimal.valueOf(30.00)));
  }

  private void loadOrders() {

    String dateStr1 = "2020-01-21";
    Date date1 = Date.valueOf(dateStr1);

    items1.add(new Item(product1.getId(), 1, order1));
    items1.add(new Item(product2.getId(), 5, order1));

    order1.setId(order1.getId());
    order1.setCustomerId(customer1.getId());
    order1.setDate(date1);
    order1.setOrderTotal(new BigDecimal("280.00"));
    order1.setItems(items1);

    orderRepository.save(order1);

    String dateStr2 = "2020-06-22";
    Date date2 = Date.valueOf(dateStr2);

    items2.add(new Item(product3.getId(), 4, order2));

    order2.setId(order2.getId());
    order2.setCustomerId(customer2.getId());
    order2.setDate(date2);
    order2.setOrderTotal(new BigDecimal("120.00"));
    order2.setItems(items2);

    orderRepository.save(order2);
  }
}
