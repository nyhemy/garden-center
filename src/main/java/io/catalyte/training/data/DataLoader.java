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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

  private final Logger logger = LoggerFactory.getLogger(DataLoader.class);

  @Autowired private UserRepository userRepository;
  @Autowired private CustomerRepository customerRepository;
  @Autowired private AddressRepository addressRepository;
  @Autowired private ProductRepository productRepository;
  @Autowired private OrderRepository orderRepository;
  @Autowired private ItemRepository itemRepository;

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

  private Item item1;
  private Item item2;
  private Item item3;
  private Item item4;

  private Set<Item> items1 = new HashSet<>();
  private Set<Item> items2 = new HashSet<>();
  private Set<Item> items3 = new HashSet<>();

  private Order order1;
  private Order order2;
  private Order order3;
  private Order order4;

  private Set<Order> orders1 = new HashSet<>();
  private Set<Order> orders2 = new HashSet<>();
  private Set<Order> orders3 = new HashSet<>();

  @Override
  public void run(String... args) throws Exception {
    logger.info("Loading data...");

    loadUsers();
    loadCustomers();
    loadAddresses();
    loadProducts();
//    loadItems();
    loadOrders();
  }

  private void loadUsers() {
    user1 = userRepository.save(
        new User("John Smith", "Smithy in Chief", new String[]{"Supervisor", "Smithy"},
            "jsmith@gmail.com",
            "mcclangers"));
    user2 = userRepository.save(
        new User("Jinky Jane", "Miner in Chief", new String[]{"Supervisor", "Miner"},
            "jjane@gmail.com",
            "saltycookies"));
    user3 = userRepository.save(
        new User("Fredo Mann", "Miner", new String[]{"Miner"}, "fmann@gmail.com",
            "diggydiggyhole"));
  }

  private void loadCustomers() {
    //change so that you build customers first then add addresses
    customer1 = customerRepository.save(new Customer("Jacob Keyes", "jkeyes@gmail.com"));
    customer2 = customerRepository.save(new Customer("Super Bee", "sbee@gmail.com"));
    customer3 = customerRepository.save(new Customer("Cest Tous", "ctous@gmail.com"));
  }

//  private void loadItems() {
//    item1 = itemRepository.save(new Item(product1, 1));
//    item2 = itemRepository.save(new Item(product2, 3));
//    item3 = itemRepository.save(new Item(product3, 2));
//    item4 = itemRepository.save(new Item(product3, 4));
//
//    Collections.addAll(items1, item1, item2);
//    Collections.addAll(items2, item3);
//    Collections.addAll(items3, item4);
//  }

  private void loadAddresses() {
    address1 = addressRepository.save(new Address("Daniel Rd", "Shrewsbury", "MA", "01545", customer1));
    address2 = addressRepository.save(new Address("Lynview Dr", "San Jose", "CA", "95135", customer2));
    address3 = addressRepository.save(new Address("Fake St", "Aiea", "HI", "96701", customer3));
  }

  private void loadProducts() {
    product1 = productRepository.save(new Product("TTNM-00004-ORE", "Ore", "Titanium Ore Grade 04",
        "Grade 04 raw titanium ore, primarily used in high stress environments.", "Charon Industries",
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
//    item1 = itemRepository.save(new Item(product1, 1));
//    item2 = itemRepository.save(new Item(product2, 3));
//    item3 = itemRepository.save(new Item(product3, 2));
//    item4 = itemRepository.save(new Item(product3, 4));
//
//    Collections.addAll(items1, item1, item2);
//    Collections.addAll(items2, item3);
//    Collections.addAll(items3, item4);

//    itemRepository.save(item1);
//    itemRepository.save(item2);
//    itemRepository.save(item3);
//    itemRepository.save(item4);

    //v1
//    order1 = orderRepository.save(new Order(customer1, Date.valueOf("2010-03-17"), BigDecimal.valueOf(10.00)));
//    order2 = orderRepository.save(new Order(customer2, Date.valueOf("2020-08-12"), BigDecimal.valueOf(10.00)));
//    order3 = orderRepository.save(new Order(customer3, Date.valueOf("2020-11-03"), BigDecimal.valueOf(10.00)));
//    order4 = orderRepository.save(new Order(customer3, Date.valueOf("2020-12-23"), BigDecimal.valueOf(10.00)));

    //v2
//    order1 = new Order(LocalDate.parse("2010-03-17"), items1, BigDecimal.valueOf(10.00), customer1);
//    order2 = new Order(LocalDate.parse("2020-08-12"), items2, BigDecimal.valueOf(10.00), customer2);
//    order3 = new Order(LocalDate.parse("2020-11-03"), items3, BigDecimal.valueOf(10.00), customer3);
//    order4 = new Order(LocalDate.parse("2020-12-23"), items1, BigDecimal.valueOf(10.00), customer3);
//
//    order1.setId(1L);
//    order2.setId(2L);
//    order3.setId(3L);
//    order4.setId(4L);
//
//    /* before order.items field is filled
//    then it's deleted when hitting the repo.save (gives "order.items is a required field" exception) */
//    orderRepository.save(order1);
//    orderRepository.save(order2);
//    orderRepository.save(order3);
//    orderRepository.save(order4);

    //v3 non-working
//    order1.setDate();
//    order1.setItems();
//    order1.setOrderTotal();
//    order1.setCustomerId();
//
//    orderRepository.save(order1);

//    Collections.addAll(orders1, order1, order2);
//    Collections.addAll(orders2, order3);
//    Collections.addAll(orders3, order4);

    // V3 Working
    SimpleDateFormat yearMonthDay = new SimpleDateFormat("yyyy-MM-dd");

    String dateStr1 = "2020-01-21";
    Date date1 = Date.valueOf(dateStr1);

    Order gcOrder1 = new Order();
    Set<Item> itemsSet1 = new HashSet<>();
    itemsSet1.add(new Item(product1.getId(), 1, gcOrder1));
    itemsSet1.add(new Item(product2.getId(), 5, gcOrder1));

    gcOrder1.setId(gcOrder1.getId());
    gcOrder1.setCustomerId(customer1);
    gcOrder1.setDate(date1);
    gcOrder1.setOrderTotal(new BigDecimal("280.00"));
    gcOrder1.setItems(itemsSet1);

    orderRepository.save(gcOrder1);

    String dateStr2 = "2020-06-22";
//    Date date1 = Date.valueOf(dateStr1);
    Date date2 = Date.valueOf(dateStr2);

    Order gcOrder2 = new Order();
    Set<Item> itemsSet2 = new HashSet<>();
    itemsSet2.add(new Item(product3.getId(), 4, gcOrder2));

    gcOrder2.setId(gcOrder2.getId());
    gcOrder2.setCustomerId(customer2);
    gcOrder2.setDate(date2);
    gcOrder2.setOrderTotal(new BigDecimal("120.00"));
    gcOrder2.setItems(itemsSet2);

    orderRepository.save(gcOrder2);
  }
}
