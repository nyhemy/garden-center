package io.catalyte.training.entities;

import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "date" + REQUIRED_FIELD)
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;

  @OneToMany(mappedBy = "order")
  @NotEmpty(message = "order_items" + REQUIRED_FIELD)
  private Set<Items> items;

  @NotNull(message = "orderTotal" + REQUIRED_FIELD)
  @Digits(integer = 999999999, fraction = 2)
  private Integer orderTotal;

  public Order() {
  }

  public Order(@NotNull(message = "date"
      + REQUIRED_FIELD) LocalDate date,
      @NotEmpty(message = "order_items"
          + REQUIRED_FIELD) Set<Items> items,
      @NotNull(message = "orderTotal"
          + REQUIRED_FIELD) @Digits(integer = 999999999, fraction = 2) Integer orderTotal) {
    this.date = date;
    this.items = items;
    this.orderTotal = orderTotal;
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", date=" + date +
        ", items=" + items +
        ", orderTotal=" + orderTotal +
        '}';
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Set<Items> getItems() {
    return items;
  }

  public void setItems(Set<Items> items) {
    this.items = items;
  }

  public Integer getOrderTotal() {
    return orderTotal;
  }

  public void setOrderTotal(Integer orderTotal) {
    this.orderTotal = orderTotal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return getId().equals(order.getId()) &&
        getDate().equals(order.getDate()) &&
        getItems().equals(order.getItems()) &&
        getOrderTotal().equals(order.getOrderTotal());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getDate(), getItems(), getOrderTotal());
  }

  @JsonIgnore
  public boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(date) &&
        Objects.isNull(items) &&
        Objects.isNull(orderTotal);
  }
}
