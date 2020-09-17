package io.catalyte.training.services;

import io.catalyte.training.entities.Address;
import io.catalyte.training.entities.Customer;
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
import java.util.Optional;
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
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private CustomerRepository customerRepository;

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
  public List<Order> queryOrdersByItem(Item item) {
    List<Order> allOrders = orderRepository.findAll();
    List<Order> orderList = new ArrayList<>();

    try {
      Example<Item> itemExample = Example.of(item);
      List<Item> itemList = itemRepository.findAll(itemExample);

      for (Order order : allOrders) {

        for (Item listItem : itemList) {

          for (Item getItem : order.getItems()) {

            if (getItem == listItem){
              orderList.add(order);
            }
          }
        }
      }
      return orderList;

    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }  }

  @Override
  // remember when adding to postman to delete ids of order and items
  public Order addOrder(Order order) {
    //V1
    if(order.getOrderTotal().scale() != 2) {
      throw new BadDataResponse("order total must have two decimal places.");
    }
    try {
      BigDecimal orderTotal = BigDecimal.valueOf(0);

      for(Item item : order.getItems()) {
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

    //V2Test
//    boolean validProductList = true;
//    boolean validCustomerId = true;
//
//    // get distinct list of productIds associated with the order
//    Long[] productIds = order.getItems().stream()
//        .map(Item::getProductId)
//        .toArray(Long[]::new);
//
//    // loop through Product Ids and make sure each is valid
//    for (Long id : productIds) {
//      try {
//
//        //if the product doesn't exist, set the flag
//        if (!productRepository.existsById(id)) {
//          validProductList = false;
//          break;
//        }
//      } catch (Exception e) {
//        throw new ServiceUnavailable(e);
//      }
//    }
//
//    // throw error if one or more products is not valid
//    if (!validProductList) {
//      throw new ResourceNotFound("could not locate product with that id");
//    }
//
//    try {
//      validCustomerId = customerRepository.existsById(order.getCustomerId());
//    } catch (Exception e) {
//      throw new ServiceUnavailable(e);
//    }
//
//    // throw error if customer is not valid
//    if (!validCustomerId) {
//      throw new ResourceNotFound("no customer found with that id");
//    }
//
//    // check if order total has exactly 2 decimal places
//    if (order.getOrderTotal().scale() != 2) {
//      throw new BadDataResponse("orderTotal must have exactly two decimal places");
//    }
//
//    // set order for every item in the order
//    for (Item item : order.getItems()) {
//      item.setOrder(order);
//    }
//
//    // save the new order
//    try {
//      return orderRepository.save(order);
//    } catch (Exception e) {
//      throw new ServiceUnavailable(e);
//    }
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
          Product product = productRepository.findById(item.getProductId()).orElse(null);
          if (product != null) {
            BigDecimal price = product.getPrice();
            Integer quantity = item.getQuantity();

            BigDecimal multiplied = price.multiply(BigDecimal.valueOf(quantity));

            orderTotal = orderTotal.add(multiplied);
          }
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
