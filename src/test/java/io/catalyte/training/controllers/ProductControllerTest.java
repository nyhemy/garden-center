package io.catalyte.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.catalyte.training.entities.Product;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//NOTE: test 4 passes in isolation, but for some reason won't when entire test class is run at once.
public class ProductControllerTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
    this.mockMvc = builder.build();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void test1_getProduct() throws Exception {
    String retType =
        mockMvc
            .perform(get("/" + "products/1"))
            .andExpect(jsonPath("$.sku", is("TTNM-00004-ORE")))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test2_queryProducts() throws Exception {
    String retType =

        mockMvc
            .perform(get("/" + "products"))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test3_saveProduct() throws Exception {
    Product product1 = new Product();
    ObjectMapper mapper = new ObjectMapper();

    product1.setId(4L);
    product1.setSku("TTNM-00003-ORE");
    product1.setType("Ore");
    product1.setName("Titanium Ore Grade 03");
    product1
        .setDescription("Grade 03 raw titanium ore, primarily used in high stress environments.");
    product1.setManufacturer("Charon Industries");
    product1.setPrice(new BigDecimal("30.00"));

    String json = mapper.writeValueAsString(product1);
    String retType =
        mockMvc
            .perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(jsonPath("$.id", is(4)))
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  // This test passes in isolation, but not when entire test class is run. No idea why.
  public void test4_updateProductById() throws Exception {
    Product product2 = new Product();
    ObjectMapper mapper = new ObjectMapper();

    product2.setId(1L);
    product2.setSku("TTNM-00003-ORE");
    product2.setType("Ore");
    product2.setName("Titanium Ore Grade 04");
    product2.setName("Titanium Ore Grade 03");
    product2
        .setDescription("Grade 03 raw titanium ore, primarily used in high stress environments.");
    product2.setManufacturer("Charon Industries");
    product2.setPrice(new BigDecimal("30.00"));

    String json = mapper.writeValueAsString(product2);
    String retType =
        mockMvc
            .perform(put("/products" + "/1", json)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Titanium Ore Grade 03"))
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test5_deleteProductById() throws Exception {
    mockMvc
        .perform(delete("/" + "products/3"))
        .andExpect(status().isNoContent());
  }
}