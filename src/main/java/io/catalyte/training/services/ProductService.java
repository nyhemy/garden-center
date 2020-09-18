package io.catalyte.training.services;

import io.catalyte.training.entities.Product;
import io.catalyte.training.entities.User;
import java.util.List;

/**
 * interface for productServiceImpl with crud methods for Product
 */
public interface ProductService {

  Product getProductById(Long id);

  List<Product> queryProducts(Product product);

  Product addProduct(Product product);

  Product updateProductById(Long id, Product product);

  void deleteProductById(Long id);

}
