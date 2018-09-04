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

import com.tick42.quicksilver.services.base.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/extensions")
public class ExtensionController {

    private final ExtensionService extensionService;
    private JwtValidator validator;

    @Autowired
    public ExtensionController(ExtensionService extensionService, JwtValidator validator) {
        this.extensionService = extensionService;
        this.validator = validator;
    }


    @PostMapping
    public ExtensionDTO create(@Valid @RequestBody ExtensionSpec extension, HttpServletRequest request) {
        int id = validator.getUserIdFromToken(request);
        return extensionService.create(extension, id);
    }

    @GetMapping(value = "/{id}")
    public ExtensionDTO get(@PathVariable(name = "id") int id, HttpServletRequest request) {
        User user = null;
        if(request.getHeader("Authorization") != null) {
            try {
                user = validator.validate(request.getHeader("Authorization").substring(6));
            }
            catch (Exception e) {
                user = null;
            }
        }
        return extensionService.findById(id, user);
    }

    @PatchMapping(value = "/{id}")
    public ExtensionDTO update(@PathVariable int id, @RequestBody ExtensionSpec extension, HttpServletRequest request) {
        int userId = validator.getUserIdFromToken(request);
        return extensionService.update(id, extension, userId);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(name = "id") int id, HttpServletRequest request) {
        int userId = validator.getUserIdFromToken(request);
        extensionService.delete(id, userId);
    }

    @GetMapping(value = "/filter")
    public PageDTO<ExtensionDTO> findAll(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "orderBy", required = false) String orderBy,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "perPage", required = false) Integer perPage) {

        return extensionService.findAll(name, orderBy, page, perPage);
    }

    @GetMapping(value = "/featured")
    public List<ExtensionDTO> featured() {
        return extensionService.findFeatured();
    }

    @GetMapping(value = "/unpublished")
    public List<ExtensionDTO> pending() {
        return extensionService.findPending();
    }

    @PatchMapping(value = "/{id}/status/{state}")
    public ExtensionDTO setPublishedState(@PathVariable(name = "id") int id, @PathVariable("state") String state) {
        return extensionService.setPublishedState(id, state);
    }

    @PatchMapping(value = "/{id}/featured/{state}")
    public ExtensionDTO setFeaturedState(@PathVariable("id") int id, @PathVariable("state") String state) {
        return extensionService.setFeaturedState(id, state);
    }

    @PatchMapping(value = "/{id}/github")
    public ExtensionDTO fetchGitHubData(@PathVariable("id") int id, HttpServletRequest request) {
        int userId = validator.getUserIdFromToken(request);
        return extensionService.fetchGitHub(id, userId);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity handleDMSRESTException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).toArray());
    }

    @ExceptionHandler
    ResponseEntity handleExtensionNotFoundException(ExtensionNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleExtensionUnavailable(ExtensionUnavailableException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleInvalidStateException(InvalidStateException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleInvalidParameterException(InvalidParameterException e) {
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
