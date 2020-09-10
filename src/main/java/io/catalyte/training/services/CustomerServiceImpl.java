package io.catalyte.training.services;

import io.catalyte.training.entities.Customer;
import io.catalyte.training.entities.User;
import io.catalyte.training.exceptions.BadDataResponse;
import io.catalyte.training.exceptions.Conflict;
import io.catalyte.training.exceptions.ResourceNotFound;
import io.catalyte.training.exceptions.ServiceUnavailable;
import io.catalyte.training.repositories.CustomerRepository;
import io.catalyte.training.repositories.UserRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private CustomerRepository customerRepository;

  @Override
  public Customer getCustomerById(Long id) {
    try {
      Customer customerLookupResult = customerRepository.findById(id).orElse(null);
      if (customerLookupResult != null) {
        return customerLookupResult;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound("Could not locate a customer with the id: " + id);
  }

  @Override
  public List<Customer> queryCustomers(Customer customer) {
    try {
      if (customer.isEmpty()) {
        return customerRepository.findAll();
      } else {
        Example<Customer> customerExample = Example.of(customer);
        return customerRepository.findAll(customerExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public Customer addCustomer(Customer customer) {
    for (Customer customerEmailCheck : customerRepository.findAll()) {
      if (customerEmailCheck.getEmail().equals(customer.getEmail())) {

        throw new Conflict("Email is already taken by another user");
      }
    }
    try {
      return customerRepository.save(customer);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public Customer updateCustomerById(Long id, Customer customer) {
    if (!customer.getId().equals(id)) {
      throw new BadDataResponse("Customer ID must match the ID specified in the URL");
    }

    for (Customer customerEmailCheck : customerRepository.findAll()) {

      if (!customerEmailCheck.getId().equals(id) && customerEmailCheck.getEmail().equals(customer.getEmail())) {

        throw new Conflict("Email is already taken by another customer");
      }
    }

    try {
      Customer customerFromDb = customerRepository.findById(id).orElse(null);

      if (customerFromDb != null) {
        return customerRepository.save(customer);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound("Could not locate a customer with the id: " + id);  }

  @Override
  public void deleteCustomerById(Long id) {
    try {
      if (customerRepository.existsById(id)) {
        customerRepository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound("Could not locate a customer with the id: " + id);
  }
}