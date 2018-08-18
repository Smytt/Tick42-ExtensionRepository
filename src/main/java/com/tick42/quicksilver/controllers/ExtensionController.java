package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.services.base.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/extension")
public class ExtensionController {

    private final ExtensionService extensionService;

    @Autowired
    public ExtensionController(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }

    @GetMapping(value = "/all")
    public List<Extension> findAll() {
        return extensionService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Extension findById(@PathVariable(name = "id") int id) {
        return extensionService.findById(id);
    }

    @GetMapping(value = "/search/{searchQuery}")
    public List<Extension> findByName(@PathVariable(name = "searchQuery") String searchQuery) {
        System.out.println(searchQuery);
        return extensionService.findByName(searchQuery);
    }
}
