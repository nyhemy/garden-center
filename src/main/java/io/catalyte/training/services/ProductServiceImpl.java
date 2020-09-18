package io.catalyte.training.services;

import io.catalyte.training.entities.Product;
import io.catalyte.training.exceptions.BadDataResponse;
import io.catalyte.training.exceptions.Conflict;
import io.catalyte.training.exceptions.ResourceNotFound;
import io.catalyte.training.exceptions.ServiceUnavailable;
import io.catalyte.training.repositories.ProductRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * service for Product
 * implements ProductService interface
 */
@Service
public class ProductServiceImpl implements ProductService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private ProductRepository productRepository;

  /**
   * Get Product by id
   *
   * @param id is the id the will be used to retrieve a Product
   * @return Product with provided id
   */
  @Override
  public Product getProductById(Long id) {
    try {
      Product productLookupResult = productRepository.findById(id).orElse(null);
      if (productLookupResult != null) {
        return productLookupResult;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound("Could not locate a product with the id: " + id);
  }

  /**
   * Queries all Products and filters through an optional query
   * Will return all Products if no query is provided
   *
   * @param product is the optional Query that will be used to filter Products
   * @return a list of Products
   */
  @Override
  public List<Product> queryProducts(Product product) {
    try {
      if (product.isEmpty()) {
        return productRepository.findAll();
      } else {
        Example<Product> productExample = Example.of(product);
        return productRepository.findAll(productExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Adds a new Product to the database
   *
   * Note: Be sure to delete Product id before posting to Postman
   *
   * @param product is the Product to be added
   * @return added Product
   */
  @Override
  public Product addProduct(Product product) {
    if (product.getPrice().scale() != 2) {
      throw new BadDataResponse("product price must have two decimal places.");
    }

    for (Product productSkuCheck : productRepository.findAll()) {
      if (productSkuCheck.getSku().equals(product.getSku())) {

        throw new Conflict("Sku is already in use by another product");
      }
    }
    try {
      return productRepository.save(product);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Updates a Product based off a provided id
   *
   * @param id is used to lookup the Product to be updated
   * @param product is the updated Product information
   * @return updated Product
   */
  @Override
  public Product updateProductById(Long id, Product product) {

    if (product.getPrice().scale() != 2) {
      throw new BadDataResponse("product price must have two decimal places");
    }

    if (!product.getId().equals(id)) {
      throw new BadDataResponse("Product ID must match the ID specified in the URL");
    }

    for (Product productSkuCheck : productRepository.findAll()) {
      if (!productSkuCheck.getId().equals(id) && productSkuCheck.getSku()
          .equals(product.getSku())) {

        throw new Conflict("Sku is already in use by another product");
      }
    }

    try {
      Product productFromDb = productRepository.findById(id).orElse(null);

      if (productFromDb != null) {
        return productRepository.save(product);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound("Could not locate a product with the id: " + id);
  }

  /**
   * Deletes a Product based off of provided id
   *
   * @param id is used to find which Product to delete
   */
  @Override
  public void deleteProductById(Long id) {
    try {
      if (productRepository.existsById(id)) {
        productRepository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound("Could not locate a product with the id: " + id);
  }
}

