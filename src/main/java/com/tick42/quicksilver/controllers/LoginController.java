package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.security.JwtGenerator;
import com.tick42.quicksilver.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class LoginController {
    private JwtGenerator jwtGenerator;
    private final UserService userService;

    public LoginController(JwtGenerator jwtGenerator, UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    @ResponseBody
    public String generate(@RequestBody User user) {
        System.out.println(userService.createTokenData(user));
        return userService.createTokenData(user);

    }
}
