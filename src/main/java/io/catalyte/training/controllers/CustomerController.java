package io.catalyte.training.controllers;


import io.catalyte.training.entities.Address;
import io.catalyte.training.entities.Customer;
import io.catalyte.training.services.CustomerService;
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
 * crud functionality for Customer entity
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

  @Autowired
  private CustomerService customerService;

  /**
   * return a Customer by their id
   * @param id is the id of the customer as a PathVariable
   * @return a Customer entity with an id of PathVariable id
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
    logger.info(new Date() + " Get by id " + id + " request received");

    return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
  }

  /**
   * Queries all Customers, returning them all or filtering through a provided query
   * Will not filter via any field in nested Address entity
   *
   * @param customer is the object that will provide the query to be made
   * @return a list of Customers
   */
  @GetMapping
  public ResponseEntity<List<Customer>> queryCustomers(Customer customer) {
    logger.info(new Date() + " Query request received: " + customer.toString());

    return new ResponseEntity<>(customerService.queryCustomers(customer), HttpStatus.OK);
  }

  /**
   * Allows for Customer querying via the fields of the nested Address entity.
   *
   * @param address is the object that will provide the query to be made for Addresses
   * @return a list of Customers
   */
  @GetMapping(value = "/address")
  public ResponseEntity<List<Customer>> getCustomerByAddress(Address address) {
    logger.info(new Date() + " Query request received: " + address.toString());

    return new ResponseEntity<>(customerService.queryCustomersByAddress(address), HttpStatus.OK);
  }

  /**
   * Saves a new Customer entity to the database if its fields are filled in properly
   * Ids of new Customers are filled in automatically.
   * @param customer is the Customer to be added.
   * @return Customer entity
   */
  @PostMapping
  public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody Customer customer) {
    logger.info(new Date() + " Post request received");

    return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.CREATED);
  }

  /**
   * Gets a Customer based off of the id and updates it with a new Customer entity
   * @param id the id of the Customer to be updated
   * @param customer is the updated Customer
   * @return the updated Customer
   */
  @PutMapping(value = "/{id}")
  public ResponseEntity<Customer> updateCustomerById(
      @PathVariable Long id, @Valid @RequestBody Customer customer) {
    logger.info(new Date() + " Put request received for id: " + id);

    return new ResponseEntity<>(customerService.updateCustomerById(id, customer), HttpStatus.OK);
  }

  /**
   * Gets a Customer based off of the id and deletes it
   * @param id is the id of the Customer to be deleted
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity deleteCustomerById(@PathVariable Long id) {
    logger.info(new Date() + " Delete request received for id: " + id);

    customerService.deleteCustomerById(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}


