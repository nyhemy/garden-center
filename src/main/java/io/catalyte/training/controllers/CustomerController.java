package io.catalyte.training.controllers;


import io.catalyte.training.entities.Customer;
import io.catalyte.training.entities.User;
import io.catalyte.training.services.CustomerService;
import io.catalyte.training.services.UserService;
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

  @PostMapping
  public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody Customer customer) {
    logger.info(new Date() + " Post request received");

    return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Customer> updateCustomerById(
      @PathVariable Long id, @Valid @RequestBody Customer customer) {
    logger.info(new Date() + " Put request received for id: " + id);

    return new ResponseEntity<>(customerService.updateCustomerById(id, customer), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity deleteCustomerById(@PathVariable Long id) {
    logger.info(new Date() + " Delete request received for id: " + id);

    customerService.deleteCustomerById(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}


