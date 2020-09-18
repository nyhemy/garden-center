package io.catalyte.training.services;

import io.catalyte.training.entities.Item;
import io.catalyte.training.entities.Order;
import io.catalyte.training.entities.Product;
import io.catalyte.training.exceptions.BadDataResponse;
import io.catalyte.training.exceptions.ResourceNotFound;
import io.catalyte.training.exceptions.ServiceUnavailable;
import io.catalyte.training.repositories.CustomerRepository;
import io.catalyte.training.repositories.ItemRepository;
import io.catalyte.training.repositories.OrderRepository;
import io.catalyte.training.repositories.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * service for Order
 * implements OrderService interface
 */
@Service
public class OrderServiceImpl implements OrderService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private CustomerRepository customerRepository;

  /**
   * Get Order by id
   *
   * @param id is the id the will be used to retrieve an Order
   * @return Order with provided id
   */
  @Override
  public Order getOrder(Long id) {
    try {
      Order orderLookupResult = orderRepository.findById(id).orElse(null);
      if (orderLookupResult != null) {
        return orderLookupResult;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound("Could not locate a order with the id: " + id);
  }

  /**
   * Queries all Orders and filters through an optional query
   * Will return all Orders if no query is provided
   * Query does not work for any fields in nested Item entity
   *
   * @param order is the optional Query that will be used to filter Orders
   * @return a list of Orders
   */
  @Override
  public List<Order> queryOrders(Order order) {
    try {
      if (order.isEmpty()) {
        return orderRepository.findAll();
      } else {
        Example<Order> orderExample = Example.of(order);
        return orderRepository.findAll(orderExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Queries Orders through nested Item entity's fields
   *
   * @param item is the object that will provide the query to be made for Items
   * @return a list of Orders
   */
  @Override
  public List<Order> queryOrdersByItem(Item item) {
    List<Order> allOrders = orderRepository.findAll();
    List<Order> orderList = new ArrayList<>();

    try {
      Example<Item> itemExample = Example.of(item);
      List<Item> itemList = itemRepository.findAll(itemExample);

      for (Order order : allOrders) {

        for (Item listItem : itemList) {

          for (Item getItem : order.getItems()) {

            if (getItem == listItem) {
              orderList.add(order);
            }
          }
        }
      }
      return orderList;

    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Adds a new Order to the database
   *
   * Note: Be sure to delete all ids, from both Item and Order, before posting in Postman
   *
   * @param order is the Order to be added
   * @return added Order
   */
  @Override
  public Order addOrder(Order order) {
    //V1

    for (Product product : productRepository.findAll()) {
      for (Item orderItem : order.getItems()) {
        if (!productRepository.existsById(orderItem.getProductId())) {
          throw new ResourceNotFound("no product found with id: " + orderItem.getProductId());
        }
      }
    }

    if (!customerRepository.existsById(order.getCustomerId())) {
      throw new ResourceNotFound("no customer found with id: " + order.getCustomerId());
    }

    if (order.getOrderTotal().scale() != 2) {
      throw new BadDataResponse("order total must have two decimal places.");
    }

    try {
      BigDecimal orderTotal = BigDecimal.valueOf(0);

      for (Item item : order.getItems()) {
        Product product = productRepository.findById(item.getProductId()).orElse(null);
        if (product != null) {
          BigDecimal price = product.getPrice();
          Integer quantity = item.getQuantity();

          BigDecimal multiplied = price.multiply(BigDecimal.valueOf(quantity));

          orderTotal = orderTotal.add(multiplied);
        }
      }
      order.setOrderTotal(orderTotal);
      for (Item item : order.getItems()) {
        item.setOrder(order);
      }
      return orderRepository.save(order);

    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Updates an Order based off a provided id
   *
   * @param id is used to lookup the Order to be updated
   * @param order is the updated Order information
   * @return updated Order
   */
  @Override
  public Order updateOrderById(Long id, Order order) {

    Order orderFromDb;

    if (!order.getId().equals(id)) {
      throw new BadDataResponse("order ID must match the ID specified in the URL");
    }

    try {
      orderFromDb = orderRepository.findById(id).orElse(null);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    if (orderFromDb != null) {
      return this.addOrder(order);
    }

    throw new ResourceNotFound("Could not locate an order with the id: " + id);
  }

  /**
   * Deletes an Order based off of provided id
   *
   * @param id is used to find which Order to delete
   */
  @Override
  public void deleteOrderById(Long id) {
    try {
      if (orderRepository.existsById(id)) {
        orderRepository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound("Could not locate an order with the id: " + id);
  }
}
