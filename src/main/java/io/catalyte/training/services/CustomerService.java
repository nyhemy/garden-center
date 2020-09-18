package io.catalyte.training.services;

import io.catalyte.training.entities.Address;
import io.catalyte.training.entities.Customer;
import java.util.List;

/**
 * interface for customerServiceImpl with crud methods for Customer
 */
public interface CustomerService {

  Customer getCustomerById(Long id);

  List<Customer> queryCustomers(Customer customer);

  List<Customer> queryCustomersByAddress(Address address);

  Customer addCustomer(Customer customer);

  Customer updateCustomerById(Long id, Customer customer);

  void deleteCustomerById(Long id);
}
