package io.catalyte.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.catalyte.training.entities.User;
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
    ObjectMapper mapper = new ObjectMapper();
    User user1 = new User();

    user1.setId(4L);
    user1.setName("Sum Bodey");
    user1.setTitle("Miner");
    user1.setRoles(new String[]{"EMPLOYEE"});
    user1.setEmail("sbodey@gmail.com");
    user1.setPassword("boimcloi");

    String gcUserAsJson = mapper.writeValueAsString(user1);

    String retType =
        mockMvc
            .perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gcUserAsJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.*", hasSize(6)))
            .andReturn()
            .getResponse()
            .getContentType();

    assertEquals("application/json", retType);

//    String json = "{\"id\":4,\"name\":\"Sum Bodey\",\"title\":\"Miner\",\"roles\":[\"Miner\",\"Forger\"],\"email\":\"sbodey@gmail.com\",\"password\":\"boimcloi\"}";
//
//    String retType =
//        this.mockMvc
//            .perform(post("/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//            .andExpect(jsonPath("$.id", is(4)))
//            .andExpect(status().isCreated())
//            .andReturn()
//            .getResponse()
//            .getContentType();
//
//    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test4_updateUserById() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    User user2 = new User();

    user2.setId(1L);
    user2.setName("Johnny Smith");
    user2.setTitle("Smithy in Chief");
    user2.setRoles(new String[]{"ADMIN"});
    user2.setEmail("jsmith@gmail.com");
    user2.setPassword("mcclangers");

    String gcUserAsJson = mapper.writeValueAsString(user2);

    String retType =
        mockMvc
            .perform(put("/users" + "/1", gcUserAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gcUserAsJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Johnny Smith"))
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);

//    String json = "{\"id\":1,\"name\":\"Johnny Smith\",\"title\":\"Smithy in Chief\",\"roles\":[\"Supervisor\",\"Smithy\"],\"email\":\"jsmith@gmail.com\",\"password\":\"mcclangers\"}";
//
//    String retType =
//        this.mockMvc
//            .perform(put("/" + "users/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//            .andExpect(jsonPath("$.name", is("Johnny Smith")))
//            .andExpect(status().isOk())
//            .andReturn()
//            .getResponse()
//            .getContentType();
//
//    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test5_deleteUserById() throws Exception {
    mockMvc
        .perform(delete("/" + "users/3"))
        .andExpect(status().isNoContent());
  }
}