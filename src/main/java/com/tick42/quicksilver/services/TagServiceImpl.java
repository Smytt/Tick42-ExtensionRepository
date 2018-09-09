package com.tick42.quicksilver.services;

import com.tick42.quicksilver.models.DTO.TagDTO;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Tag;
import com.tick42.quicksilver.repositories.base.ExtensionRepository;
import com.tick42.quicksilver.repositories.base.TagRepository;
import com.tick42.quicksilver.services.base.ExtensionService;
import com.tick42.quicksilver.services.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagDTO findByName(String name) {
        return new TagDTO(tagRepository.findByName(name));
    }

    @Override
    public String normalize(String name) {
        name = name.trim().replaceAll(" +", "-");
        name = name.toLowerCase();
        return name;
    }

    @Override
    public List<Tag> prepareTags(List<Tag> tags) {
        for (int i = 0; i < tags.size(); i++) {
            Tag tag = tags.get(i);
            Tag existingTag = tagRepository.findByName(tag.getName());
            if (existingTag != null) {
                tags.set(i, existingTag);
            }
        }
        return tags;
    }

    @Override
    public Set<Tag> generateTags(String tagString) {
        tagString = tagString.trim();
        Set<Tag> tags = new HashSet<>();

        if (tagString == null || tagString.equals("")) {
            return tags;
        }

        List<String> tagNames =
                Arrays.stream(tagString.split(","))
                        .map(String::toLowerCase)
                        .map(String::trim)
                        .distinct()
                        .collect(Collectors.toList());

        tagNames.forEach(tagName -> {
            Tag existingTag = tagRepository.findByName(tagName);
            if (existingTag != null) {
                tags.add(existingTag);
            } else {
                tags.add(new Tag(tagName));
            }
        });

        return tags;
    }

}
