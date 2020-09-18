package io.catalyte.training.controllers;

import io.catalyte.training.entities.Product;
import io.catalyte.training.services.ProductService;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * crud functionality for Product entity
 */
@RestController
@RequestMapping("/products")
public class ProductController {

  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private ProductService productService;

  /**
   * Gets a Product based off of the provided id
   * @param id of the Product to be returned
   * @return Product with provided id
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable Long id) {
    logger.info(new Date() + " Get by id " + id + " request received");

    return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
  }

  /**
   * Queries all Products, returning them all or filtering through a provided query
   * @param product is the object that will provide the query to be made
   * @return a list of Products
   */
  @GetMapping
  public ResponseEntity<List<Product>> queryProducts(Product product) {
    logger.info(new Date() + " Query request received: " + product.toString());

    return new ResponseEntity<>(productService.queryProducts(product), HttpStatus.OK);
  }

  /**
   * Saves a new Product entity to the database if its fields are filled in properly
   * Ids of new Products are filled in automatically.
   * @param product is the Product to be added.
   * @return Product entity
   */
  @PostMapping
  public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product) {
    logger.info(new Date() + " Post request received");

    return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
  }

  /**
   * Gets a Product based off of the id and updates it with a new Product entity
   * @param id of the Product to be updated
   * @param product is the updated Product
   * @return the updated Product
   */
  @PutMapping(value = "/{id}")
  public ResponseEntity<Product> updateProductById(
      @PathVariable Long id, @Valid @RequestBody Product product) {
    logger.info(new Date() + " Put request received for id: " + id);

    return new ResponseEntity<>(productService.updateProductById(id, product), HttpStatus.OK);
  }

  /**
   * Gets a Product based off of the id and deletes it
   * @param id of the Product to be deleted
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity deleteProductById(@PathVariable Long id) {
    logger.info(new Date() + " Delete request received for id: " + id);

    productService.deleteProductById(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
