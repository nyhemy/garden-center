package io.catalyte.training.controllers;


import io.catalyte.training.entities.Customer;
import io.catalyte.training.entities.User;
import io.catalyte.training.services.CustomerService;
import io.catalyte.training.services.UserService;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

  @Autowired
  private CustomerService customerService;

  @GetMapping(value = "/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
    logger.info(new Date() + " Get by id " + id + " request received");

    return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Customer>> queryCustomers(Customer customer) {
    logger.info(new Date() + " Query request received: " + customer.toString());

    return new ResponseEntity<>(customerService.queryCustomers(customer), HttpStatus.OK);
  }
}


