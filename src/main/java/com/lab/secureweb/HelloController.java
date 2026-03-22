package com.lab.secureweb;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

    @GetMapping({"/", "/hello"})
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping(value = "/api/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> apiHello() {
        return Map.of("message", "Greetings from Spring Boot!", "secure", "true");
    }
}
