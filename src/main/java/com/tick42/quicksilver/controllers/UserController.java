package com.tick42.quicksilver.controllers;


import com.tick42.quicksilver.exceptions.UsernameExistsException;
import com.tick42.quicksilver.models.DTO.AuthDTO;
import com.tick42.quicksilver.models.DTO.UserDTO;
import com.tick42.quicksilver.models.DTO.UserPublicDTO;
import com.tick42.quicksilver.models.Spec.UserSpec;
import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.services.base.UserService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    public AuthDTO generateTokenOnLogin(@RequestBody User user, HttpServletResponse response) throws InvalidCredentialsException {
        User loggedUser = userService.login(user);
        String token = "Token " + userService.generateToken(loggedUser);
        return new AuthDTO(loggedUser, token);
    }

    @PostMapping(value = "/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping(value = "/{id}")
    public UserPublicDTO profile(@PathVariable(name = "id") int id) {
        return userService.findById(id);
    }

    @PatchMapping(value = "/setState/{id}/{newState}")
    public User setState(@PathVariable("newState") String state,
                         @PathVariable("id") int id) {
       return userService.setState(id, state);
    }

    @GetMapping(value = "/all")
    public List<UserDTO> listAllUsers() {
        return userService.findAll();
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
