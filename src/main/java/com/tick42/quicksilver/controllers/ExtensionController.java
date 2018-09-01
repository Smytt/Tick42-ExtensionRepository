package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.Spec.ExtensionSpec;
import com.tick42.quicksilver.security.JwtValidator;
import com.tick42.quicksilver.services.base.ExtensionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/extension")
public class ExtensionController {

    private final ExtensionService extensionService;
    private JwtValidator validator;

    @Autowired
    public ExtensionController(ExtensionService extensionService, JwtValidator validator) {
        this.extensionService = extensionService;
        this.validator = validator;
    }

    @GetMapping(value = "/all")
    public List<ExtensionDTO> findAll(
            @RequestParam(name = "orderBy", required = false) String orderBy,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "perPage", required = false) Integer perPage) {

        if (orderBy == null) {
            orderBy = "date";
        }

        if (page == null) {
            page = 1;
        }

        if (perPage == null) {
            perPage = 10;
        }

        return extensionService.findAll(orderBy, page, perPage);
    }

    @GetMapping(value = "/{id}")
    public ExtensionDTO findById(@PathVariable(name = "id") int id) {
        return extensionService.findById(id);
    }

    @GetMapping(value = "/search/{name}")
    public List<ExtensionDTO> findByName(@PathVariable(name = "name") String name) {
        return extensionService.findByName(name);
    }

    @GetMapping(value = "/featured")
    public List<ExtensionDTO> featured() {
        return extensionService.findFeatured();
    }

    @GetMapping(value = "/userExtensions")
    public List<ExtensionDTO> featured(HttpServletRequest request, HttpServletResponse response) {
        int id = validator.getUserIdFromToken(request, response);
        return extensionService.findUserExtensions(id);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping(value = "/approveExtension/{id}/secured")
    void acceptExtension(@PathVariable(name = "id") int id) {
        extensionService.approveExtension(id);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping(value = "/changeFeaturedState/{id}/{newState}/secured")
    void changeFeaturedState(@PathVariable("id") int id, @PathVariable("newState") String newState) {
        extensionService.changeFeaturedState(id, newState);
    }

    @GetMapping(value = "/findByTag/{tag}")
    public List<ExtensionDTO> findByTag(@PathVariable String tag) {
        return extensionService.findByTag(tag);
    }

    @PostMapping(value = "/add")
    public @ResponseBody
    ExtensionDTO addExtension(@Valid @RequestBody ExtensionSpec extension) {
        return extensionService.create(extension);
    }

}
