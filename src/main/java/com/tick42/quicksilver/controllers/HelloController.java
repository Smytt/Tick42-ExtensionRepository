package com.tick42.quicksilver.controllers;


import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/hello")
public class HelloController {
    @Secured("ROLE_ADMIN")
    @GetMapping
    public String hello() {
        return "Hello World";
    }
}
