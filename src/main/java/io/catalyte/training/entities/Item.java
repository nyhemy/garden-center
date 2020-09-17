package io.catalyte.training.entities;

import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "items")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @NotNull (message = "product" + REQUIRED_FIELD)
  @Valid
  private Product product;

  @NotNull (message = "quantity" + REQUIRED_FIELD)
  @Min(value = 0, message = "value should be greater than zero")
  private Integer quantity;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="order_id")
  @JsonIgnore
  private Order order;

  public Item() {
  }

  public Item(@NotNull(message = "product"
      + REQUIRED_FIELD) @Valid Product product,
      @NotNull(message = "quantity"
          + REQUIRED_FIELD) @Min(value = 0, message = "value should be greater than zero") Integer quantity,
      Order order) {
    this.product = product;
    this.quantity = quantity;
    this.order = order;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  @Override
  public String toString() {
    return "Item{" +
        "id=" + id +
        ", product=" + product +
        ", quantity=" + quantity +
        ", order=" + order +
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
    Item item = (Item) o;
    return Objects.equals(id, item.id) &&
        Objects.equals(product, item.product) &&
        Objects.equals(quantity, item.quantity) &&
        Objects.equals(order, item.order);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, product, quantity, order);
  }

  @JsonIgnore
  public boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(product) &&
        Objects.isNull(quantity) &&
        Objects.isNull(order);
  }
}
