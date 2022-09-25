package com.example.airlineproject.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Controller for RestAPI, return JSON
public class FirstApiController {
    @GetMapping("/api/hello")
    public String hello(){
        return "hello world";
    }

}
