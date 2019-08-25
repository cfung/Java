package edu.udacity.java.nano;

import edu.udacity.java.nano.chat.WebSocketChatServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(WebSocketChatServer.class)
@ContextConfiguration(classes=WebSocketChatApplication.class)
//@SpringBootTest
public class chatroomTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contexLoads() {
        assertNotNull(mockMvc);
    }

    @Test
    public void testLogin() throws Exception{

        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("/login"));
    }

    @Test
    public void testChat() throws Exception{

        this.mockMvc.perform(post("/chat")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("chat"));
    }

}