package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.services.base.ExtensionService;
import com.tick42.quicksilver.services.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/tags")
public class TagController {

    private final TagService tagService;
    private final ExtensionService extensionService;

    @Autowired
    public TagController(TagService tagService, ExtensionService extensionService) {
        this.tagService = tagService;
        this.extensionService = extensionService;
    }
}
