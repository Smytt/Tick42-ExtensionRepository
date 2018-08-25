package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.security.JwtGenerator;
import com.tick42.quicksilver.services.base.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    boolean registered(@RequestBody User user) {
        return userService.registered(user);

    }
}
