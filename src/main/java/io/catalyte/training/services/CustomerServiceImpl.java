package io.catalyte.training.services;

import io.catalyte.training.entities.Address;
import io.catalyte.training.entities.Customer;
import io.catalyte.training.exceptions.BadDataResponse;
import io.catalyte.training.exceptions.Conflict;
import io.catalyte.training.exceptions.ResourceNotFound;
import io.catalyte.training.exceptions.ServiceUnavailable;
import io.catalyte.training.repositories.AddressRepository;
import io.catalyte.training.repositories.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * service for Customer
 * implements CustomerService interface
 */
@Service
public class CustomerServiceImpl implements CustomerService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private AddressRepository addressRepository;

  String[] stateAbbrevs = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID",
      "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE",
      "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN",
      "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};

  String zipCodeRegex = "^[0-9]{5}(?:-[0-9]{4})?$";
  Pattern zipCodePattern = Pattern.compile(zipCodeRegex);

  /**
   * Get Customer by id
   *
   * @param id is the id the will be used to retrieve a Customer
   * @return Customer with provided id
   */
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

  /**
   * Queries all Customers and filters through an optional query
   * Will return all Customers if no query is provided
   * Query does not work for any fields in nested Address entity
   *
   * Note: One can still query Customer via Address using queryCustomer, the url would look like:
   * "http://localhost:8080/customers?address.city=San Jose"
   *
   * @param customer is the optional Query that will be used to filter Customers
   * @return a list of Customers
   */
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

  /**
   * Queries Customers through nested Address entity's fields
   *
   * @param address is the object that will provide the query to be made for Addresses
   * @return a list of Customers
   */
  @Override
  public List<Customer> queryCustomersByAddress(Address address) {
    // takes in an address field
    // returns an answer based on that field
    List<Customer> allCustomers = customerRepository.findAll();
    List<Customer> customerList = new ArrayList<>();

    try {
      Example<Address> addressExample = Example.of(address);
      List<Address> addressList = addressRepository.findAll(addressExample);

      for (Customer customer : allCustomers) {

        for (Address a : addressList) {

          if (customer.getAddress() == a) {
            customerList.add(customer);
          }
        }
      }
      return customerList;

    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Adds a new Customer to the database
   *
   * Note: Be sure to delete all ids, from both address and customer, before posting in Postman
   *
   * @param customer is the Customer to be added
   * @return added Customer
   */
  @Override
  public Customer addCustomer(Customer customer) {

    Matcher matcher = zipCodePattern.matcher(customer.getAddress().getZipCode());

    if (!matcher.matches()) {
      throw new BadDataResponse("Invalid zipcode");
    }

    for (String state : stateAbbrevs) {
      if (customer.getAddress().getState().equals(state)) {

        for (Customer customerEmailCheck : customerRepository.findAll()) {
          if (customerEmailCheck.getEmail().equals(customer.getEmail())) {
            throw new Conflict("Email is already taken by another user");
          }
        }
        try {
          customer.getAddress().setCustomer(customer);
          return customerRepository.save(customer);
        } catch (Exception e) {
          throw new ServiceUnavailable(e);
        }
      }
    }
    throw new BadDataResponse("Invalid state");
  }

  /**
   * Updates a customer based off a provided id
   *
   * @param id is used to lookup the Customer to be updated
   * @param customer is the updated Customer information
   * @return updated Customer
   */
  @Override
  public Customer updateCustomerById(Long id, Customer customer) {
    if (!customer.getId().equals(id)) {
      throw new BadDataResponse("Customer ID must match the ID specified in the URL");
    }

    Matcher matcher = zipCodePattern.matcher(customer.getAddress().getZipCode());

    if (!matcher.matches()) {
      throw new BadDataResponse("Invalid zipcode");
    }

    for (String state : stateAbbrevs) {
      if (customer.getAddress().getState().equals(state)) {

        for (Customer customerEmailCheck : customerRepository.findAll()) {

          if (!customerEmailCheck.getId().equals(id) && customerEmailCheck.getEmail()
              .equals(customer.getEmail())) {
            throw new Conflict("Email is already taken by another customer");
          }
        }

        try {
          Customer customerFromDb = customerRepository.findById(id).orElse(null);

          if (customerFromDb != null) {
            customer.getAddress().setId(customerFromDb.getAddress().getId());
            customer.getAddress().setCustomer(customer);
            return customerRepository.save(customer);
          }
        } catch (Exception e) {
          throw new ServiceUnavailable(e);
        }

        throw new ResourceNotFound("Could not locate a customer with the id: " + id);
      }
    }
    throw new BadDataResponse("Invalid state");
  }

  /**
   * Deletes a Customer based off of provided id
   *
   * @param id is used to find which Customer to delete
   */
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