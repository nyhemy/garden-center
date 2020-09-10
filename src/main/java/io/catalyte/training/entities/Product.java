package io.catalyte.training.entities;

import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Products")
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
  private BigDecimal price;
}
