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

  /*
  Note: One can still query Customer via Address using queryCustomer, the url would look like:
  "http://localhost:8080/customers?address.city=San Jose". However, this is considered bad practice.
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

  @Override
  //be sure to delete all ids, from both address and customer, before posting
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