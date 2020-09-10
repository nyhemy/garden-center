package io.catalyte.training.services;

import io.catalyte.training.entities.Customer;
import java.util.List;

public interface CustomerService {

  Customer getCustomerById(Long id);

  List<Customer> queryCustomers(Customer customer);
}
