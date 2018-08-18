package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.services.base.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/api/extension")
public class ExtensionConteroller {

    private final ExtensionService extensionService;

    @Autowired
    public ExtensionConteroller(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }

    @GetMapping(name = "/{id}")
    public Extension getExtensionById(@PathVariable(name = "id") int id) {
        return extensionService.findById(id);
    }
}
