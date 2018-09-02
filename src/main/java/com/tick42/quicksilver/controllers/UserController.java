package com.tick42.quicksilver.controllers;


import com.tick42.quicksilver.exceptions.UsernameExistsException;
import com.tick42.quicksilver.models.DTO.UserDTO;
import com.tick42.quicksilver.models.Spec.UserSpec;
import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.security.models.JwtUser;
import com.tick42.quicksilver.services.base.UserService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public JwtUser generateTokenOnLogin(@RequestBody User user, HttpServletResponse response) throws InvalidCredentialsException {
        User loggedUser = userService.login(user);
        String token = "Token " + userService.generateToken(loggedUser);
        return new JwtUser(loggedUser, token);
    }

    @PostMapping(value = "/register")
    public User register(@RequestBody UserSpec user) {
        return userService.register(user);
    }

    @GetMapping(value = "/{id}")
    public UserDTO profile(@PathVariable(name = "id") int id) {
        return userService.findById(id);
    }

    @PatchMapping(value = "/setState/{id}/{newState}")
    public UserDTO setState(@PathVariable("newState") String state,
                         @PathVariable("id") int id) {
       return userService.setState(id, state);
    }

    @GetMapping(value = "/get")
    public List<UserDTO> listAllUsers(@RequestParam(name = "state", required = false) String state) {
        return userService.findAll(state);
    }
    @ExceptionHandler
    ResponseEntity handleInvalidCredentialsException(InvalidCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleUsernameExistsException(UsernameExistsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
