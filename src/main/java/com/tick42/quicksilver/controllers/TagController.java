package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Tag;
import com.tick42.quicksilver.services.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/{tag}")
    public List<Extension> findByTag(@PathVariable String tag) {
        tagService.normalize(tag);
        return tagService.findExtensionsByTag(tag);
    }
//    @PostMapping(value = "/add")
//    public @ResponseBody Extension addExtension(@RequestBody Tag tag) {
//        return tagService.create(tag);
//    }
}
