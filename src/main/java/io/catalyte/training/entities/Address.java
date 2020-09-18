package io.catalyte.training.entities;

import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

/**
 * Template for Address entity, and everything it needs to be loaded properly into a database
 * Contains fields, constructors, getters/setters, toString(), equals(), hashCode(), and isEmpty()
 * Relationships: Customer = OneToOne
 */
@Entity
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "street" + REQUIRED_FIELD)
  private String street;

  @NotBlank(message = "city" + REQUIRED_FIELD)
  private String city;

  @NotBlank(message = "state" + REQUIRED_FIELD)
  private String state;

  @NotBlank(message = "zipcode" + REQUIRED_FIELD)
  private String zipCode;

  @OneToOne(fetch = FetchType.EAGER)
  @JsonIgnore
  private Customer customer;

  public Address() {
  }

  public Address(@NotBlank(message = "street"
      + REQUIRED_FIELD) String street,
      @NotBlank(message = "city" + REQUIRED_FIELD) String city,
      @NotBlank(message = "state"
          + REQUIRED_FIELD) String state,
      @NotBlank(message = "zipcode"
          + REQUIRED_FIELD) String zipCode, Customer customer) {
    this.street = street;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
    this.customer = customer;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  @Override
  public String toString() {
    return "Address{" +
        "id=" + id +
        ", street='" + street + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", zipCode='" + zipCode + '\'' +
        ", customer=" + customer +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Address address = (Address) o;
    return Objects.equals(id, address.id) &&
        Objects.equals(street, address.street) &&
        Objects.equals(city, address.city) &&
        Objects.equals(state, address.state) &&
        Objects.equals(zipCode, address.zipCode) &&
        Objects.equals(customer, address.customer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, street, city, state, zipCode, customer);
  }

  @JsonIgnore
  public boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(street) &&
        Objects.isNull(city) &&
        Objects.isNull(state) &&
        Objects.isNull(zipCode) &&
        Objects.isNull(customer);
  }
}
