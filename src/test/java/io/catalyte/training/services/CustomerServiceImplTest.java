package io.catalyte.training.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.catalyte.training.entities.Address;
import io.catalyte.training.entities.Customer;
import io.catalyte.training.entities.User;
import io.catalyte.training.repositories.AddressRepository;
import io.catalyte.training.repositories.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

public class CustomerServiceImplTest {

  @Mock
  CustomerRepository customerRepository;
  @Mock
  AddressRepository addressRepository;
  @InjectMocks
  CustomerServiceImpl customerServiceImpl;

  Customer testCustomer1;
  Customer testCustomer2;

  Address testAddress1;
  Address testAddress2;

  List<Customer> testCustomersList = new ArrayList<Customer>();
  List<Address> testAddressList = new ArrayList<Address>();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    testCustomer1 = new Customer("Jacob Keyes", "jkeyes@gmail.com");
    testCustomer2 = new Customer("Super Bee", "sbee@gmail.com");

    testCustomer1.setId(1L);
    testCustomer2.setId(2L);

    testAddress1 = new Address("Daniel Rd", "Shrewsbury", "MA", "01545", testCustomer1);
    testAddress2 = new Address("Lynview Dr", "San Jose", "CA", "95135", testCustomer2);

    testAddress1.setId(1L);
    testAddress2.setId(2L);

    testCustomer1.setAddress(testAddress1);
    testCustomer2.setAddress(testAddress2);

    testCustomersList.add(testCustomer1);
    testAddressList.add(testAddress1);

    when(customerRepository.findById(any(Long.class)))
        .thenReturn(Optional.of(testCustomersList.get(0)));
    when(customerRepository.save(any(Customer.class))).thenReturn(testCustomersList.get(0));
    when(customerRepository.findAll()).thenReturn(testCustomersList);
    when(customerRepository.findAll(any(Example.class))).thenReturn(testCustomersList);

    when(addressRepository.findAll()).thenReturn(testAddressList);
    when(addressRepository.findAll(any(Example.class))).thenReturn(testAddressList);

  }

  @Test
  public void getCustomerById() {
    Customer result = customerServiceImpl.getCustomerById(Long.valueOf(1));
    Assert.assertEquals(testCustomer1, result);
  }

  @Test
  public void queryCustomers() {
    List<Customer> result = customerServiceImpl.queryCustomers(new Customer());

    Assert.assertEquals(testCustomersList, result);
  }

  @Test
  public void testQueryCustomersExample() {
    List<Customer> result = customerServiceImpl.queryCustomers(testCustomer1);

    Assert.assertEquals(testCustomersList, result);
  }

  @Test
  public void queryCustomersByAddress() {
    List<Customer> result = customerServiceImpl.queryCustomersByAddress(testAddress1);

    Assert.assertEquals(testCustomersList, result);
  }

  @Test
  public void addCustomer() {
    when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer2);

    Customer result = customerServiceImpl.addCustomer(testCustomer2);
    Assert.assertEquals(testCustomer2, result);
  }

  @Test
  public void updateCustomerById() {
    Customer result = customerServiceImpl.updateCustomerById(1L, testCustomer1);
    Assert.assertEquals(testCustomer1, result);
  }

  @Test
  public void deleteCustomerById() {
    when(customerRepository.existsById(anyLong())).thenReturn(true);
    customerServiceImpl.deleteCustomerById(Long.valueOf(1));

    verify(customerRepository).deleteById(any());
  }
}