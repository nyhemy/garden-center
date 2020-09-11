package io.catalyte.training.entities;

import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "sku" + REQUIRED_FIELD)
  private String sku;

  @NotBlank(message = "type" + REQUIRED_FIELD)
  private String type;

  @NotBlank(message = "name" + REQUIRED_FIELD)
  private String name;

  @NotBlank(message = "description" + REQUIRED_FIELD)
  private String description;

  @NotBlank(message = "manufacturer" + REQUIRED_FIELD)
  private String manufacturer;

  @NotNull(message = "price" + REQUIRED_FIELD)
  @Digits(integer = 999999999, fraction = 2)
  private BigDecimal price;

  public Product() {
  }

  public Product(
      @NotBlank(message = "sku" + REQUIRED_FIELD) String sku,
      @NotBlank(message = "type" + REQUIRED_FIELD) String type,
      @NotBlank(message = "name" + REQUIRED_FIELD) String name,
      @NotBlank(message = "description"
          + REQUIRED_FIELD) String description,
      @NotBlank(message = "manufacturer"
          + REQUIRED_FIELD) String manufacturer,
      @NotNull(message = "price"
          + REQUIRED_FIELD) BigDecimal price) {
    this.sku = sku;
    this.type = type;
    this.name = name;
    this.description = description;
    this.manufacturer = manufacturer;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", sku='" + sku + '\'' +
        ", type='" + type + '\'' +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", manufacturer='" + manufacturer + '\'' +
        ", price=" + price +
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
    Product product = (Product) o;
    return Objects.equals(getId(), product.getId()) &&
        Objects.equals(getSku(), product.getSku()) &&
        Objects.equals(getType(), product.getType()) &&
        Objects.equals(getName(), product.getName()) &&
        Objects.equals(getDescription(), product.getDescription()) &&
        Objects.equals(getManufacturer(), product.getManufacturer()) &&
        Objects.equals(getPrice(), product.getPrice());
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(getId(), getSku(), getType(), getName(), getDescription(), getManufacturer(),
            getPrice());
  }

  @JsonIgnore
  public boolean isEmpty(){
    return Objects.isNull(id) &&
        Objects.isNull(sku) &&
        Objects.isNull(type) &&
        Objects.isNull(name) &&
        Objects.isNull(description) &&
        Objects.isNull(manufacturer) &&
        Objects.isNull(price);
  }
}
