package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.exceptions.*;
import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.DTO.PageDTO;
import com.tick42.quicksilver.models.Spec.ExtensionSpec;
import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.security.JwtValidator;
import com.tick42.quicksilver.services.base.ExtensionService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.tick42.quicksilver.services.base.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ExtensionController {

    private final ExtensionService extensionService;
    private JwtValidator validator;
    private RatingService ratingService;

    @Autowired
    public ExtensionController(ExtensionService extensionService, JwtValidator validator, RatingService ratingService) {
        this.extensionService = extensionService;
        this.validator = validator;
        this.ratingService = ratingService;
    }

    @GetMapping("/extensions/{id}")
    public ExtensionDTO get(@PathVariable(name = "id") int id, HttpServletRequest request) {
        User user = null;
        int rating = 0;
        if (request.getHeader("Authorization") != null) {
            try {
                user = validator.validate(request.getHeader("Authorization").substring(6));
                rating = ratingService.userRatingForExtension(id, user.getId());
            } catch (Exception e) {
                user = null;
            }
        }
        ExtensionDTO extensionDTO = extensionService.findById(id, user);
        extensionDTO.setCurrentUserRatingValue(rating);
        return extensionDTO;
    }

    @GetMapping("/extensions/filter")
    public PageDTO<ExtensionDTO> findAll(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "orderBy", required = false) String orderBy,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "perPage", required = false) Integer perPage) {

        return extensionService.findAll(name, orderBy, page, perPage);
    }

    @GetMapping("/extensions/featured")
    public List<ExtensionDTO> featured() {
        return extensionService.findFeatured();
    }

    @GetMapping("/auth/extensions/download/{id}")
    public ExtensionDTO download(@PathVariable(name = "id") int id) {
        return extensionService.increaseDownloadCount(id);
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @PostMapping("/auth/extensions")
    public ExtensionDTO create(@Valid @RequestBody ExtensionSpec extension, HttpServletRequest request) {
        int id = validator.getUserIdFromToken(request);
        return extensionService.create(extension, id);
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @PatchMapping("/auth/extensions/{id}")
    public ExtensionDTO update(@PathVariable int id, @Valid @RequestBody ExtensionSpec extension, HttpServletRequest request) {
        int userId = validator.getUserIdFromToken(request);
        return extensionService.update(id, extension, userId);
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @DeleteMapping("/auth/extensions/{id}")
    public void delete(@PathVariable(name = "id") int id, HttpServletRequest request) {
        int userId = validator.getUserIdFromToken(request);
        extensionService.delete(id, userId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/auth/extensions/unpublished")
    public List<ExtensionDTO> pending() {
        return extensionService.findPending();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/auth/extensions/{id}/status/{state}")
    public ExtensionDTO setPublishedState(@PathVariable(name = "id") int id, @PathVariable("state") String state) {
        return extensionService.setPublishedState(id, state);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/auth/extensions/{id}/featured/{state}")
    public ExtensionDTO setFeaturedState(@PathVariable("id") int id, @PathVariable("state") String state) {
        return extensionService.setFeaturedState(id, state);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/auth/extensions/{id}/github")
    public ExtensionDTO fetchGitHubData(@PathVariable("id") int id, HttpServletRequest request) {
        int userId = validator.getUserIdFromToken(request);
        return extensionService.fetchGitHub(id, userId);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity handleInvalidExtensionSpecException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(x -> x.getDefaultMessage())
                        .toArray());
    }

    @ExceptionHandler
    ResponseEntity handleExtensionNotFoundException(ExtensionNotFoundException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
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
    ResponseEntity handleInvalidStateException(InvalidStateException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleInvalidParameterException(InvalidParameterException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleGitHubRepositoryException(GitHubRepositoryException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleUnauthorizedExtensionModificationException(UnauthorizedExtensionModificationException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

}
