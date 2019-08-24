package edu.udacity.java.nano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
public class WebSocketChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketChatApplication.class, args);
    }

    /**
     * Login Page
     */
    @GetMapping("/")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    /**
     * Chatroom Page
     */
    @GetMapping("/index")
    //@RequestMapping(value="/login", method = RequestMethod.POST)
    public ModelAndView index(String username, HttpServletRequest request) throws UnknownHostException {
        //TODO: add code for login to chatroom.
        ModelAndView modelAndView = new ModelAndView("chat");
        modelAndView.addObject("username", username);

        System.out.println("/index is called...");
        return modelAndView;
    }

    @RequestMapping(value="/chat", method = RequestMethod.POST)
    public ModelAndView chat(String username, HttpServletRequest request) throws UnknownHostException {
        //TODO: add code for login to chatroom.
        ModelAndView modelAndView = new ModelAndView("chat");
        modelAndView.addObject("username", username);

        System.out.println("/chat is called...");
        return modelAndView;
    }
}
