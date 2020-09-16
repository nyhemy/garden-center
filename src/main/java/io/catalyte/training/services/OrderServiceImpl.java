package io.catalyte.training.services;

import io.catalyte.training.entities.Address;
import io.catalyte.training.entities.Customer;
import io.catalyte.training.entities.Item;
import io.catalyte.training.entities.Order;
import io.catalyte.training.exceptions.BadDataResponse;
import io.catalyte.training.exceptions.ResourceNotFound;
import io.catalyte.training.exceptions.ServiceUnavailable;
import io.catalyte.training.repositories.ItemRepository;
import io.catalyte.training.repositories.OrderRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private ItemRepository itemRepository;

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

//  @Override
//  public List<Order> queryOrdersByItem(Item item) {
//    // takes in an item field
//    // returns an answer based on that field
//    List<Order> allOrders = orderRepository.findAll();
//
//    try {
//      if (item.isEmpty()) {
//        return allOrders;
//
//      } else {
//        Example<Item> itemExample = Example.of(item);
//
//        List<Item> itemList = itemRepository.findAll(itemExample);
//        List<Order> orderList = new ArrayList<>();
//
//        for (Order order : allOrders) {
//
//          for (Item i : itemList) {
//
//            for (Item i2 : order.getItems()) {
//
//              if (i2 == i) {
//                orderList.add(order);
//              }
//            }
//          }
//        }
//
//
//        return orderList;
//      }
//    } catch (Exception e) {
//      throw new ServiceUnavailable(e);
//    }
//  }

  @Override
  public Order addOrder(Order order) {
    if(order.getOrderTotal().scale() != 2) {
      throw new BadDataResponse("order total must have two decimal places.");
    }
    try {
      BigDecimal orderTotal = BigDecimal.valueOf(0);

      for(Item item : order.getItems()) {
        BigDecimal price = item.getProduct().getPrice();
        Integer quantity = item.getQuantity();

        BigDecimal multiplied = price.multiply(BigDecimal.valueOf(quantity));

        orderTotal = orderTotal.add(multiplied);
      }
      order.setOrderTotal(orderTotal);
      return orderRepository.save(order);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public Order updateOrderById(Long id, Order order) {

    if(order.getOrderTotal().scale() != 2) {
      throw new BadDataResponse("order total must have two decimal places.");
    }

    if (!order.getId().equals(id))
    {
      throw new BadDataResponse("Order ID must match the ID specified in the URL");
    }

    try {
      Order orderFromDb = orderRepository.findById(id).orElse(null);

      if (orderFromDb != null) {
        BigDecimal orderTotal = BigDecimal.valueOf(0);

        for(Item item : order.getItems()) {
          BigDecimal price = item.getProduct().getPrice();
          Integer quantity = item.getQuantity();

          BigDecimal multiplied = price.multiply(BigDecimal.valueOf(quantity));

          orderTotal = orderTotal.add(multiplied);
        }
        order.setOrderTotal(orderTotal);
        return orderRepository.save(order);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the vehicle
    throw new ResourceNotFound("Could not locate an order with the id: " + id);  }

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
