package io.catalyte.training.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.catalyte.training.entities.Product;
import io.catalyte.training.entities.User;
import io.catalyte.training.repositories.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

public class ProductServiceImplTest {

  @Mock
  ProductRepository productRepository;
  @InjectMocks
  ProductServiceImpl productServiceImpl;

  Product testProduct1;
  Product testProduct2;

  List<Product> testList = new ArrayList<Product>();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    testProduct1 = new Product("TTNM-00004-ORE", "Ore", "Titanium Ore Grade 04",
        "Grade 04 raw titanium ore, primarily used high stress environments.", "Charon Industries",
        BigDecimal.valueOf(30.00));
    testProduct2 = new Product("TTNM-00023-ALY", "Alloy", "Titanium Alloy Grade 23",
        "Grade 23 titanium alloy, superior choice for various rugged fabrications.",
        "Charon Industries",
        BigDecimal.valueOf(50.00));

    testProduct1.setId(1L);
    testProduct2.setId(2L);

    testList.add(testProduct1);
    when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(testList.get(0)));
    when(productRepository.save(any(Product.class))).thenReturn(testList.get(0));
    when(productRepository.findAll()).thenReturn(testList);
    when(productRepository.findAll(any(Example.class))).thenReturn(testList);
  }

  @Test
  public void getProductById() {
    Product result = productServiceImpl.getProductById(Long.valueOf(1));
    Assert.assertEquals(testProduct1, result);
  }

  @Test
  public void queryProducts() {
    List<Product> result = productServiceImpl.queryProducts(new Product());

    Assert.assertEquals(testList, result);
  }

  @Test
  public void testQueryUserExample() {
    List<Product> result = productServiceImpl.queryProducts(testProduct1);

    Assert.assertEquals(testList, result);
  }

  @Test
  public void addProduct() {
    when(productRepository.save(any(Product.class))).thenReturn(testProduct2);

    Product result = productServiceImpl.addProduct(testProduct2);
    Assert.assertEquals(testProduct2, result);
  }

  @Test
  public void updateProductById() {
    Product result = productServiceImpl.updateProductById(1L, testProduct1);
    Assert.assertEquals(testProduct1, result);
  }

  @Test
  public void deleteProductById() {
    when(productRepository.existsById(anyLong())).thenReturn(true);
    productServiceImpl.deleteProductById(Long.valueOf(1));

    verify(productRepository).deleteById(any());
  }
}