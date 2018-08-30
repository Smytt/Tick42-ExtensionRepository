package com.tick42.quicksilver.controllers;


import com.tick42.quicksilver.exceptions.UsernameExistsException;
import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.security.JwtGenerator;
import com.tick42.quicksilver.services.base.UserService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
    public String generateTokenOnLogin(@RequestBody User user, HttpServletResponse response) throws InvalidCredentialsException {
        User loggedUser = userService.login(user);
        String token = "Token " + userService.generateToken(loggedUser);
        return token;
    }

    @PostMapping(value = "/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping(value = "/{id}/secured")
    public User profile(@PathVariable(name = "id") int id) {
        return userService.findById(id);
    }

    @PatchMapping(value="/changeActiveState/{newState}/{username}/secured")
    public void changeUserState(@PathVariable("newState") String state,
                                @PathVariable("username") String username){
        User user = userService.findByUsername(username);
        userService.changeActiveState(user,state);
    }
    @Secured("ROLE_ADMIN")
    @GetMapping(value="/listAll/secured")
    public List<User> listAllUsers(){
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
