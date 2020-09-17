package io.catalyte.training.services;

import static org.junit.Assert.*;

import io.catalyte.training.entities.Item;
import io.catalyte.training.entities.Order;
import io.catalyte.training.entities.Product;
import io.catalyte.training.repositories.ItemRepository;
import io.catalyte.training.repositories.OrderRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OrderServiceImplTest {

  @Mock
  OrderRepository orderRepository;
  @Mock
  ItemRepository itemRepository;
  @InjectMocks
  OrderServiceImpl orderServiceImpl;

  Product testProduct1;
  Product testProduct2;

  Item testItem1;
  Item testItem2;

  Order testOrder1;
  Order testOrder2;

  List<Order> testOrderList = new ArrayList<Order>();
  List<Item> testItemList = new ArrayList<Item>();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    testProduct1 = new Product("TTNM-00004-ORE", "Ore", "Titanium Ore Grade 04",
        "Grade 04 raw titanium ore, primarily used high stress environments.", "Charon Industries",
        BigDecimal.valueOf(30.00));
    testProduct2 = new Product("TTNM-00023-ALY", "Alloy", "Titanium Alloy Grade 23",
        "Grade 23 titanium alloy, superior choice for various rugged fabrications.",
        "Charon Industries",
        BigDecimal.valueOf(50.00));

    testItem1 = new Item(testProduct1.getId(), 1, testOrder1);
    testItem2 = new Item(testProduct2.getId(), 3, testOrder2);

//    testOrder1 = new Order();
//    testOrder2 = new Order();
  }

  @Test
  public void getOrder() {
  }

  @Test
  public void queryOrders() {
  }

  @Test
  public void queryOrdersByItem() {
  }

  @Test
  public void addOrder() {
  }

  @Test
  public void updateOrderById() {
  }

  @Test
  public void deleteOrderById() {
  }
}