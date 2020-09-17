package io.catalyte.training.controllers;

import io.catalyte.training.entities.Address;
import io.catalyte.training.entities.Customer;
import io.catalyte.training.entities.Item;
import io.catalyte.training.entities.Order;
import io.catalyte.training.services.OrderService;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
  private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

  @Autowired private OrderService orderService;

  @GetMapping(value = "/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    logger.info(new Date() + " Get by id " + id + " request received");

    return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Order>> queryOrders(Order order) {
    logger.info(new Date() + " Query request received: " + order.toString());

    return new ResponseEntity<>(orderService.queryOrders(order), HttpStatus.OK);
  }

  @GetMapping(value = "/item")
  public ResponseEntity<List<Order>> getOrdersByItem(Item item) {
    logger.info(new Date() + " Query request received: " + item.toString());

    return new ResponseEntity<>(orderService.queryOrdersByItem(item), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Order> saveOrder(@Valid @RequestBody Order order) {
    logger.info(new Date() + " Post request received");

    return new ResponseEntity<>(orderService.addOrder(order), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Order> updateOrderById(
      @PathVariable Long id, @Valid @RequestBody Order order) {
    logger.info(new Date() + " Put request received for id: " + id);

    return new ResponseEntity<>(orderService.updateOrderById(id, order), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity deleteOrderById(@PathVariable Long id) {
    logger.info(new Date() + " Delete request received for id: " + id);

    orderService.deleteOrderById(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
