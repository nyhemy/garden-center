package io.catalyte.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
//NOTE: test 5 passes in isolation, but for some reason won't when entire test class is run at once.
public class CustomerControllerTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
    this.mockMvc = builder.build();
  }

  @Test
  public void test1_getCustomer() throws Exception {

    String retType =
        mockMvc
            .perform(get("/customers/1"))
            .andExpect(jsonPath("$.name", is("Jacob Keyes")))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test2_queryCustomers() throws Exception {

    String retType =
        mockMvc
            .perform(get("/customers"))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test3_queryCustomersByAddress() throws Exception {

    String retType =
        mockMvc
            .perform(get("/customers/address"))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test4_saveCustomer() throws Exception {
    String json = "{\"id\":4,\"name\":\"Gabriel Reyes\",\"email\":\"greyes@gmail.com\",\"address\":{\"id\":1,\"street\":\"Daniel Rd\",\"city\":\"Shrewsbury\",\"state\":\"MA\",\"zipCode\":\"01545\"}}";

    String retType =
        mockMvc
            .perform(post("/" + "customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(jsonPath("$.id", is(4)))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  // This test passes in isolation, but not when entire test class is run. No idea why.
  public void test5_updateCustomerById() throws Exception {
    String json = "{\"id\":1,\"name\":\"Miranda Keyes\",\"email\":\"jkeyes@gmail.com\",\"address\":{\"id\":1,\"street\":\"Daniel Rd\",\"city\":\"Shrewsbury\",\"state\":\"MA\",\"zipCode\":\"01545\"}}";

    String retType =
        mockMvc
            .perform(put("/" + "customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(jsonPath("$.name", is("Miranda Keyes")))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test6_deleteCustomerById() throws Exception {
    mockMvc
        .perform(delete("/" + "customers/3"))
        .andExpect(status().isNoContent());
  }
}