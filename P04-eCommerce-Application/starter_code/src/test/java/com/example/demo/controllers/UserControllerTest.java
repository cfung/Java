package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JacksonTester<CreateUserRequest> json;

    @Before
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }


    @Test
    public void testCreateUser() throws Exception {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test1");
        createUserRequest.setPassword("password1");
        createUserRequest.setConfirmPassword("password1");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/user/create"))
                .content(json.write(createUserRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assert.assertNotNull(result.getResponse().getContentAsString());

    }

    @Test
    public void testFindUserUserByName() throws Exception {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test1");
        createUserRequest.setPassword("password1");
        createUserRequest.setConfirmPassword("password1");

        MvcResult request = mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/user/create"))
                .content(json.write(createUserRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        System.out.println("request: " + request);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(new URI("/api/user/test1"))
                .content(json.write(createUserRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assert.assertNotNull(result.getResponse().getContentAsString());

    }

    @Test
    public void testFindUserUserByID() throws Exception {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test1");
        createUserRequest.setPassword("password1");
        createUserRequest.setConfirmPassword("password1");

        MvcResult request = mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/user/create"))
                .content(json.write(createUserRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Integer id = JsonPath.read(request.getResponse().getContentAsString(), "$.id");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(new URI("/api/user/id/" + id))
                .content(json.write(createUserRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assert.assertNotNull(result.getResponse().getContentAsString());

    }

    @Test
    public void testBadPassword() throws Exception {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test1");
        createUserRequest.setPassword("pass");
        createUserRequest.setConfirmPassword("pass");

        mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/user/create"))
                .content(json.write(createUserRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testMisMatchPassword() throws Exception {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test1");
        createUserRequest.setPassword("password1");
        createUserRequest.setConfirmPassword("password2");

        mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/user/create"))
                .content(json.write(createUserRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testEmptyUsername() throws Exception {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("");
        createUserRequest.setPassword("password1");
        createUserRequest.setConfirmPassword("password2");

        mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/user/create"))
                .content(json.write(createUserRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testUsernameInHiASCIIChar() throws Exception {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("testáèö");
        createUserRequest.setPassword("password1");
        createUserRequest.setConfirmPassword("password1");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/user/create"))
                .content(json.write(createUserRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assert.assertNotNull(result.getResponse().getContentAsString());

    }
}
