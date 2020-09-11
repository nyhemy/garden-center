package io.catalyte.training.entities;

import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//@Entity
//@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @NotNull (message = "customer" + REQUIRED_FIELD)
  private Customer customer;

  @NotNull(message = "date" + REQUIRED_FIELD)
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;

//  private Items items;

  @NotNull(message = "orderTotal" + REQUIRED_FIELD)
  @Digits(integer = 999999999, fraction = 2)
  private Integer orderTotal;

}
