package com.tick42.quicksilver.controllers;


import com.tick42.quicksilver.exceptions.*;
import com.tick42.quicksilver.models.DTO.UserDTO;
import com.tick42.quicksilver.models.Spec.ChangeUserPasswordSpec;
import com.tick42.quicksilver.models.Spec.UserSpec;
import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.security.Exceptions.JwtExpiredTokenException;
import com.tick42.quicksilver.security.Exceptions.JwtTokenIsMissingException;
import com.tick42.quicksilver.security.JwtValidator;
import com.tick42.quicksilver.security.models.JwtUser;
import com.tick42.quicksilver.services.base.UserService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private JwtValidator validator;

    public UserController(UserService userService, JwtValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @PostMapping(value = "/users/login")
    @ResponseBody
    public JwtUser login(@RequestBody User user, HttpServletResponse response) throws InvalidCredentialsException {
        User loggedUser = userService.login(user);
        String token = "Token " + userService.generateToken(loggedUser);
        return new JwtUser(loggedUser, token);
    }

    @PostMapping(value = "/users/register")
    public User register(@Valid @RequestBody UserSpec user) {
        return userService.register(user,"ROLE_USER");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "auth/users/adminRegistration")
    public User registerAdmin(@Valid @RequestBody UserSpec user){
        return userService.register(user, "ROLE_ADMIN");
    }

    @GetMapping(value = "/users/{id}")
    public UserDTO profile(@PathVariable(name = "id") int id, HttpServletRequest request) {
        User loggedUser = null;
        if(request.getHeader("Authorization") != null) {
            try {
                loggedUser = validator.validate(request.getHeader("Authorization").substring(6));
            }
            catch (Exception e) {
                loggedUser = null;
            }
        }
        return userService.findById(id, loggedUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/auth/users/setState/{id}/{newState}")
    public UserDTO setState(@PathVariable("newState") String state,
                         @PathVariable("id") int id) {
       return userService.setState(id, state);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/auth/users/all")
    public List<UserDTO> listAllUsers(@RequestParam(name = "state", required = false) String state) {
        return userService.findAll(state);
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/auth/changePassword")
    public UserDTO changePassword(@Valid @RequestBody ChangeUserPasswordSpec changePasswordSpec, HttpServletRequest request){
        int userId = validator.getUserIdFromToken(request);
        return userService.changePassword(userId, changePasswordSpec);
    }

    @ExceptionHandler
    ResponseEntity handleInvalidCredentialsException(InvalidCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleJwtExpiredTokenException(JwtExpiredTokenException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleJwtTokenIsMissingException(JwtTokenIsMissingException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
    @ExceptionHandler
    ResponseEntity handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleUsernameExistsException(UsernameExistsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handlePasswordsMissMatchException(PasswordsMissMatchException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleInvalidStateException(InvalidStateException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleDisabledUserException(BlockedUserException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }


    @ExceptionHandler
    ResponseEntity handleUserProfileUnavailableException(UserProfileUnavailableException e){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity handleInvalidUserSpecException(MethodArgumentNotValidException e)
    {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getBindingResult().
                        getFieldErrors()
                        .stream()
                        .map(x -> x.getDefaultMessage())
                        .toArray());
    }
}
