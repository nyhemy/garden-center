package io.catalyte.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerControllerTest {
  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  ResultMatcher okStatus = MockMvcResultMatchers.status().isOk();
  ResultMatcher createdStatus = MockMvcResultMatchers.status().isCreated();
  ResultMatcher deletedStatus = MockMvcResultMatchers.status().isNoContent();
  ResultMatcher notFoundStatus = MockMvcResultMatchers.status().isNotFound();
  ResultMatcher expectedType = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8);

  @Before
  public void setUp() {
    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
    this.mockMvc = builder.build();
  }

  @Test
  public void test1_getCustomer() throws Exception{
    mockMvc
        .perform(get("/" + "customers/1"))
        .andExpect(jsonPath("$.name", is("Jacob Keyes")))
        .andExpect(okStatus)
        .andExpect(expectedType);
  }

  @Test
  public void test2_queryCustomers() throws Exception{
    mockMvc
        .perform(get("/" + "customers"))
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(okStatus)
        .andExpect(expectedType);
  }

  @Test
  public void test3_getCustomerByAddress() throws Exception{
  }

  @Test
  public void test4_saveCustomer() throws Exception{
    String json = "{\"id\":4,\"name\":\"Gabriel Reyes\",\"email\":\"greyes@gmail.com\",\"address\":{\"id\":1,\"street\":\"Daniel Rd\",\"city\":\"Shrewsbury\",\"state\":\"MA\",\"zipCode\":\"01545\"}}";

    this.mockMvc
        .perform(post("/" + "customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(jsonPath("$.id", is(4)))
        .andExpect(createdStatus)
        .andExpect(expectedType);
  }

  @Test
  public void test5_updateCustomerById() throws Exception{
    String json = "{\"id\":1,\"name\":\"Miranda Keyes\",\"email\":\"jkeyes@gmail.com\",\"address\":{\"id\":1,\"street\":\"Daniel Rd\",\"city\":\"Shrewsbury\",\"state\":\"MA\",\"zipCode\":\"01545\"}}";

    this.mockMvc

        .perform(put("/" + "customers/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(jsonPath("$.name", is("Miranda Keyes")))
        .andExpect(okStatus)
        .andExpect(expectedType);
  }

  @Test
  public void test6_deleteCustomerById() throws Exception{
    mockMvc
        .perform(delete("/" + "customers/3"))
        .andExpect(deletedStatus);
  }
}