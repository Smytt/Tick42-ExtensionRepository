package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.security.JwtGenerator;
import com.tick42.quicksilver.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class LoginController {
    private JwtGenerator jwtGenerator;
    private final UserService userService;

    public LoginController(JwtGenerator jwtGenerator, UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    void generate(@RequestBody User user) {
        userService.createTokenData(user);

    }
}
