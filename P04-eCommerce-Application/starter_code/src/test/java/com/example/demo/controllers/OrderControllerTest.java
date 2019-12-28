package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Assert;
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
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class OrderControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private JacksonTester<CreateUserRequest> jsonCreateUser;

    @Autowired
    private JacksonTester<ModifyCartRequest> jsonModifyCart;

    private String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoYWhhMSIsImV4cCI6MTU3ODM3NjU0NH0.SCJfDAC4UHKqWTCOrhjzfgi6gF9UhEu6bYmjAolwuNofTotreqhZk9sTLd9wDOe3lT79zmM70-MX1rWBI5lPLg";

    @Test
    public void testSubmitOrder() throws Exception {

        createUser("haha");

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("haha");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(1);

        mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/cart/addToCart"))
                .content(jsonModifyCart.write(modifyCartRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
                .andReturn();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/order/submit/haha"))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
                .andReturn();

        Assert.assertNotNull(result.getResponse().getContentAsString());


    }

    private void createUser (String username) throws Exception {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(username);
        createUserRequest.setPassword("password1");
        createUserRequest.setConfirmPassword("password1");

        mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/user/create"))
                .content(jsonCreateUser.write(createUserRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

    }
}
