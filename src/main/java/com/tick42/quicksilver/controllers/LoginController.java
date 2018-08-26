package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.security.JwtGenerator;
import com.tick42.quicksilver.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public String generate(@RequestBody User user,HttpServletResponse response) {
        return userService.createTokenData(user, response);

    }
}
