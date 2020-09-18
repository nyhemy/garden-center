package io.catalyte.training.services;

import io.catalyte.training.entities.Item;
import io.catalyte.training.entities.Order;
import java.util.List;

/**
 * interface for orderServiceImpl with crud methods for Order
 */
public interface OrderService {

  Order getOrder(Long id);

  List<Order> queryOrders(Order order);

  List<Order> queryOrdersByItem(Item item);

  Order addOrder(Order order);

  Order updateOrderById(Long id, Order order);

  void deleteOrderById(Long id);
}
