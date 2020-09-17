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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  ResultMatcher okStatus = MockMvcResultMatchers.status().isOk();
  ResultMatcher createdStatus = MockMvcResultMatchers.status().isCreated();
  ResultMatcher deletedStatus = MockMvcResultMatchers.status().isNoContent();
  ResultMatcher notFoundStatus = MockMvcResultMatchers.status().isNotFound();
  ResultMatcher expectedType = MockMvcResultMatchers.content()
      .contentType(MediaType.APPLICATION_JSON_UTF8);

  @Before
  public void setup() {
    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
    this.mockMvc = builder.build();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void test1_getUser() throws Exception {
    String retType =
        mockMvc
            .perform(get("/" + "users/1"))
            .andExpect(jsonPath("$.name", is("John Smith")))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);

//    mockMvc
//        .perform(get("/" + "users/1"))
//        .andExpect(jsonPath("$.name", is("John Smith")))
//        .andExpect(okStatus)
//        .andExpect(expectedType);
  }

  @Test
  public void test2_queryUsers() throws Exception {
    String retType =

        mockMvc
            .perform(get("/" + "users"))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test3_saveUser() throws Exception {
    String json = "{\"id\":4,\"name\":\"Sum Bodey\",\"title\":\"Miner\",\"roles\":[\"Miner\",\"Forger\"],\"email\":\"sbodey@gmail.com\",\"password\":\"boimcloi\"}";

    String retType =
        this.mockMvc
            .perform(post("/users")
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
  public void test4_updateUserById() throws Exception {
    String json = "{\"id\":1,\"name\":\"Johnny Smith\",\"title\":\"Smithy in Chief\",\"roles\":[\"Supervisor\",\"Smithy\"],\"email\":\"jsmith@gmail.com\",\"password\":\"mcclangers\"}";

    String retType =
    this.mockMvc
        .perform(put("/" + "users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(jsonPath("$.name", is("Johnny Smith")))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test5_deleteUserById() throws Exception {
    mockMvc
        .perform(delete("/" + "users/3"))
        .andExpect(deletedStatus);
  }
}