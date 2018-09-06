package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.exceptions.ExtensionUnavailableException;
import com.tick42.quicksilver.exceptions.UnauthorizedExtensionModificationException;
import com.tick42.quicksilver.exceptions.UserNotFoundException;
import com.tick42.quicksilver.security.JwtValidator;
import com.tick42.quicksilver.services.base.ExtensionService;
import com.tick42.quicksilver.services.base.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api")
public class RatingController {
    private final ExtensionService extensionService;
    private final RatingService ratingService;
    private JwtValidator validator;

    @Autowired
    public RatingController(ExtensionService extensionService, RatingService ratingService, JwtValidator validator) {
        this.extensionService = extensionService;
        this.ratingService = ratingService;
        this.validator = validator;
    }

    @PatchMapping(value = "/auth/rating/{id}/{rating}")
    public int rating(@PathVariable("id") int id, @PathVariable("rating") int rating, HttpServletRequest request) {
        int userId = validator.getUserIdFromToken(request);
        return ratingService.rate(id, rating, userId);
    }

    @GetMapping(value = "/auth/userRating/{id}")
    public int userRatingForExtension(@PathVariable("id") int id, HttpServletRequest request) {
        int userId = validator.getUserIdFromToken(request);
        return ratingService.userRatingForExtension(id, userId);
    }

    @ExceptionHandler
    ResponseEntity handleUnauthorizedExtensionModificationException(UnauthorizedExtensionModificationException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleExtensionUnavailable(ExtensionUnavailableException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
