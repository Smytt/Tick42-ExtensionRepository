package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.DTO.PageDTO;
import com.tick42.quicksilver.models.Spec.ExtensionSpec;
import com.tick42.quicksilver.security.JwtValidator;
import com.tick42.quicksilver.services.base.ExtensionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.parameters.P;
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
    public @ResponseBody
    ExtensionDTO create(@Valid @RequestBody ExtensionSpec extension) {
        return extensionService.create(extension);
    }

    @GetMapping(value = "/{id}")
    public ExtensionDTO get(@PathVariable(name = "id") int id) {
        return extensionService.findById(id);
    }

    @PutMapping(value = "/{id}")
    public ExtensionDTO update(@PathVariable(name = "id") int id) {
        return extensionService.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public ExtensionDTO delete(@PathVariable(name = "id") int id) {
        return extensionService.findById(id);
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

//    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/pending")
    public List<ExtensionDTO> pending() {
        return extensionService.findPending();
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping(value = "/{id}/approve/secured")
    void acceptExtension(@PathVariable(name = "id") int id) {
        extensionService.approveExtension(id);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping(value = "/{id}/featured/{newState}/secured")
    void changeFeaturedState(@PathVariable("id") int id, @PathVariable("newState") String newState) {
        extensionService.changeFeaturedState(id, newState);
    }

}
