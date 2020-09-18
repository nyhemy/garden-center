package io.catalyte.training.controllers;

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

/**
 * crud functionality for Order entity
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

  private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

  @Autowired
  private OrderService orderService;

  /**
   * Gets an Order based off of the provided id
   * @param id is the id of the Order to be returned
   * @return Order with provided id
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    logger.info(new Date() + " Get by id " + id + " request received");

    return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.OK);
  }

  /**
   * Queries all orders, returning them all or filtering through a provided query
   * Will not filter via any field in nested Item entity
   * @param order is the object that will provide the query to be made
   * @return a list of Orders
   */
  @GetMapping
  public ResponseEntity<List<Order>> queryOrders(Order order) {
    logger.info(new Date() + " Query request received: " + order.toString());

    return new ResponseEntity<>(orderService.queryOrders(order), HttpStatus.OK);
  }

  /**
   *Allows for Order querying via the fields of the nested Item entity.
   * @param item is the object that will provide the query to be made for Items
   * @return a list of Orders
   */
  @GetMapping(value = "/item")
  public ResponseEntity<List<Order>> getOrdersByItem(Item item) {
    logger.info(new Date() + " Query request received: " + item.toString());

    return new ResponseEntity<>(orderService.queryOrdersByItem(item), HttpStatus.OK);
  }

  /**
   * Saves a new Order entity to the database if its fields are filled in properly
   * Ids of new Orders are filled in automatically.
   * @param order is the Order to be added.
   * @return Order entity
   */
  @PostMapping
  public ResponseEntity<Order> saveOrder(@Valid @RequestBody Order order) {
    logger.info(new Date() + " Post request received");

    return new ResponseEntity<>(orderService.addOrder(order), HttpStatus.CREATED);
  }

  /**
   * Gets an Order based off of the id and updates it with a new Order entity
   * @param id of the Order to be updated
   * @param order is the updated Order
   * @return the updated Order
   */
  @PutMapping(value = "/{id}")
  public ResponseEntity<Order> updateOrderById(
      @PathVariable Long id, @Valid @RequestBody Order order) {
    logger.info(new Date() + " Put request received for id: " + id);

    return new ResponseEntity<>(orderService.updateOrderById(id, order), HttpStatus.OK);
  }

  /**
   * Gets an Order based off of the id and deletes it
   * @param id of the Order to be deleted
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity deleteOrderById(@PathVariable Long id) {
    logger.info(new Date() + " Delete request received for id: " + id);

    orderService.deleteOrderById(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
