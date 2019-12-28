package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoYWhhMSIsImV4cCI6MTU3ODM3NjU0NH0.SCJfDAC4UHKqWTCOrhjzfgi6gF9UhEu6bYmjAolwuNofTotreqhZk9sTLd9wDOe3lT79zmM70-MX1rWBI5lPLg";

    @Test
    public void testGetAllItems() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(new URI("/api/item"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
                .andReturn();

        List<Integer> ids = JsonPath.read(result.getResponse().getContentAsString(), "$..id");
        Assert.assertEquals(2, ids.size());
    }

    @Test
    public void testFindItemByName() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(new URI("/api/item/name/Square%20Widget"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
                .andReturn();

        List<Integer> ids = JsonPath.read(result.getResponse().getContentAsString(), "$..id");
        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testFindItemByID() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(new URI("/api/item/1"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
                .andReturn();

        List<Integer> ids = JsonPath.read(result.getResponse().getContentAsString(), "$..id");
        Assert.assertEquals(1, ids.size());
    }
}
