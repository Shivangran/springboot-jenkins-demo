package com.example.docker_demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello(){
        return "Hello from Spring Boot in Docker!";
    }

    @GetMapping("/health")
    public String health(){
        return "Application is running healthy!";
    }
}