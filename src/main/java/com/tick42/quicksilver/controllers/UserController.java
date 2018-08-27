package com.tick42.quicksilver.controllers;


import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.security.JwtGenerator;
import com.tick42.quicksilver.services.base.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class UserController {
    private JwtGenerator jwtGenerator;
    private final UserService userService;

    public UserController(JwtGenerator jwtGenerator, UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public String generate(@RequestBody User user, HttpServletResponse response) {
        return userService.createTokenData(user, response);

    }

    @PostMapping(value = "/register")
    public User registered(@RequestBody User user) {
        return userService.register(user);

    }
}
