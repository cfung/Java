package com.example.demo.controllers;

import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private JacksonTester<CreateUserRequest> jsonCreateUser;

    @Autowired
    private JacksonTester<ModifyCartRequest> jsonModifyCart;

    private String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoYWhhMSIsImV4cCI6MTU3ODM3NjU0NH0.SCJfDAC4UHKqWTCOrhjzfgi6gF9UhEu6bYmjAolwuNofTotreqhZk9sTLd9wDOe3lT79zmM70-MX1rWBI5lPLg";

    private void createUser(String username) throws Exception {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(username);
        createUserRequest.setPassword("password1");
        createUserRequest.setConfirmPassword("password1");

        mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/user/create"))
                .content(jsonCreateUser.write(createUserRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void testAddToCart() throws Exception {
        createUser("hahaCartUser1");

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("hahaCartUser1");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/cart/addToCart"))
                .content(jsonModifyCart.write(modifyCartRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
                .andReturn();

        String productName = JsonPath.read(result.getResponse().getContentAsString(), "$.items[0].name");
        assertEquals("Round Widget", productName);

    }
}
