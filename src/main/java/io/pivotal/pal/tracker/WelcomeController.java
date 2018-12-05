package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    private String msg;
    @GetMapping("/")
    @ResponseBody
    public String sayHello() {
        return msg;
    }
    public WelcomeController(@Value("${WELCOME_MESSAGE}") String msg) {
         //public WelcomeController(String msg) {
        this.msg=msg;
    }

    public WelcomeController() {
        this("Hello from test");
    }
}