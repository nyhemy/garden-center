package io.catalyte.training.entities;


import static io.catalyte.training.constants.StringConstants.INVALID_EMAIL;
import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "name" + REQUIRED_FIELD)
  private String name;

  @NotBlank(message = "email" + REQUIRED_FIELD)
  @Email(message = "email" + INVALID_EMAIL)
  private String email;

  @OneToOne
  @NotNull(message = "address" + REQUIRED_FIELD)
  private Address address;

//  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
//  @JsonIgnore
//  private Set<Order> orders = new HashSet<>();

  public Customer() {
  }

  public Customer(
      @NotBlank(message = "name" + REQUIRED_FIELD) String name,
      @NotBlank(message = "email"
          + REQUIRED_FIELD) @Email(message = "email"
          + INVALID_EMAIL) String email,
      @NotNull(message = "address"
          + REQUIRED_FIELD) Address address) {
    this.name = name;
    this.email = email;
    this.address = address;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", address=" + address +
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
    Customer customer = (Customer) o;
    return Objects.equals(getId(), customer.getId()) &&
        Objects.equals(getName(), customer.getName()) &&
        Objects.equals(getEmail(), customer.getEmail()) &&
        Objects.equals(getAddress(), customer.getAddress());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getEmail(), getAddress());
  }

  @JsonIgnore
  public boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(name) &&
        Objects.isNull(email) &&
        Objects.isNull(address);
  }
}
