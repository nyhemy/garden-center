package io.catalyte.training.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.catalyte.training.entities.Address;
import io.catalyte.training.entities.Customer;
import io.catalyte.training.entities.Item;
import io.catalyte.training.entities.Order;
import io.catalyte.training.repositories.CustomerRepository;
import io.catalyte.training.repositories.ItemRepository;
import io.catalyte.training.repositories.OrderRepository;
import io.catalyte.training.repositories.ProductRepository;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

public class OrderServiceImplTest {

  @Mock
  OrderRepository orderRepository;
  @Mock
  ItemRepository itemRepository;
  @Mock
  ProductRepository productRepository;
  @Mock
  CustomerRepository customerRepository;
  @InjectMocks
  OrderServiceImpl orderServiceImpl;


  Item testItem1;
  Item testItem2;

  Order testOrder1;
  Order testOrder2;

  List<Order> testOrderList = new ArrayList<Order>();
  Set<Item> testItemsSet = new HashSet<Item>();
  List<Item> testItemsList = new ArrayList<Item>();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    testOrder1 = new Order(1L, Date.valueOf("2020-06-22"), new BigDecimal("280.00"));
    testOrder2 = new Order(2L, Date.valueOf("2020-06-22"), new BigDecimal("120.00"));
    testOrder1.setId(1L);
    testOrder2.setId(2L);

    testItem1 = new Item(1L, 1, testOrder1);
    testItem2 = new Item(2L, 3, testOrder2);
    testItem1.setId(1L);
    testItem2.setId(2L);
    testItemsSet.add(testItem1);
    testItemsList.add(testItem1);

    testOrder1.setItems(testItemsSet);
    testOrder2.setItems(testItemsSet);
    testOrderList.add(testOrder1);

    when(orderRepository.findById(any(Long.class))).thenReturn(Optional.of(testOrderList.get(0)));
    when(orderRepository.save(any(Order.class))).thenReturn(testOrderList.get(0));
    when(orderRepository.findAll()).thenReturn(testOrderList);
    when(orderRepository.findAll(any(Example.class))).thenReturn(testOrderList);

    when(customerRepository.existsById(any(Long.class))).thenReturn(true);
    when(itemRepository.findAll(any(Example.class))).thenReturn(testItemsList);

  }

  @Test
  public void getOrder() {
    Order result = orderServiceImpl.getOrder(Long.valueOf(1));
    Assert.assertEquals(testOrder1, result);
  }

  @Test
  public void queryOrders() {
    List<Order> result = orderServiceImpl.queryOrders(new Order());

    Assert.assertEquals(testOrderList, result);
  }

  @Test
  public void queryOrdersByItem() {
    List<Order> result = orderServiceImpl.queryOrdersByItem(testItem1);

    Assert.assertEquals(testOrderList, result);
  }

  @Test
  public void addOrder() {
    when(orderRepository.save(any(Order.class))).thenReturn(testOrder2);

    Order result = orderServiceImpl.addOrder(testOrder2);
    Assert.assertEquals(testOrder2, result);
  }

  @Test
  public void updateOrderById() {
    Order result = orderServiceImpl.updateOrderById(1L, testOrder1);
    Assert.assertEquals(testOrder1, result);
  }

  @Test
  public void deleteOrderById() {
    when(orderRepository.existsById(anyLong())).thenReturn(true);
    orderServiceImpl.deleteOrderById(Long.valueOf(1));

    verify(orderRepository).deleteById(any());
  }
}