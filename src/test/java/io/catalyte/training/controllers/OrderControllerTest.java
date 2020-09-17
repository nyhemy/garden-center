package io.catalyte.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.catalyte.training.entities.Item;
import io.catalyte.training.entities.Order;
import io.catalyte.training.entities.Product;
import io.catalyte.training.repositories.CustomerRepository;
import io.catalyte.training.repositories.ItemRepository;
import io.catalyte.training.repositories.OrderRepository;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderControllerTest {

  @Autowired
  OrderRepository gcOrderRepository;

  @Autowired
  ItemRepository itemRepository;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
    this.mockMvc = builder.build();
  }

  @Test
  public void test1_getOrderById() throws Exception {
    String retType =
        mockMvc
            .perform(get("/orders/1"))
            .andExpect(jsonPath("$.customerId", is(1)))
            .andExpect(jsonPath("$.date", is("2020-01-21")))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test2_queryOrders() throws Exception {
    String retType =
        mockMvc
            .perform(get("/orders"))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test3_getOrdersByItem() throws Exception {
    String retType =
        mockMvc
            .perform(get("/orders/item"))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test4_saveOrder() throws Exception {
//    Order order = new Order();
//
//    Set<Item> items = new HashSet<>();
//    items.add(new Item(1L, 1, order));
//
//    ObjectMapper mapper = new ObjectMapper();
//
//    order.setId(3L);
//    order.setCustomerId(3L);
//    order.setDate(Date.valueOf("2020-04-06"));
//    order.setItems(items);
//    order.setOrderTotal(new BigDecimal("280"));
//
//    String json = mapper.writeValueAsString(order);
//    String retType =
//        mockMvc
//            .perform(post("/orders")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//            .andExpect(jsonPath("$.id", is(3)))
//            .andReturn()
//            .getResponse()
//            .getContentType();
//
//    assertEquals("application/json", retType);
    String ret =
        mockMvc
            .perform(get("/orders/1"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    String retType =
        mockMvc
            .perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ret))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.date").value("2020-01-21"))
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test5_updateOrderById() throws Exception {
    String ret =
        mockMvc
            .perform(get("/orders/1"))
            .andExpect(jsonPath("$.date").value("2020-01-21"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    // Update date
    ret = ret.replace("2020-01-21", "2020-06-22");

    // Post altered order
    String retType =
        mockMvc
            .perform(put("/orders" + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ret))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.date").value("2020-06-22"))
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test6_deleteOrderById() throws Exception {
    mockMvc
        .perform(delete("/" + "orders/2"))
        .andExpect(status().isNoContent());
  }
}