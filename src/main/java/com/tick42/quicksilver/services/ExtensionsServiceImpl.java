package com.tick42.quicksilver.services;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Tag;
import com.tick42.quicksilver.repositories.base.ExtensionRepository;
import com.tick42.quicksilver.repositories.base.TagRepository;
import com.tick42.quicksilver.services.base.ExtensionService;
import com.tick42.quicksilver.services.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExtensionsServiceImpl implements ExtensionService {

    private final ExtensionRepository extensionRepository;
    private final TagService tagService;

    @Autowired
    public ExtensionsServiceImpl(ExtensionRepository extensionRepository, TagService tagService) {
        this.extensionRepository = extensionRepository;
        this.tagService = tagService;
    }

    @Override
    public Extension create(Extension extension) {

        extension.setUploadDate(new Date());

        List<Tag> tags = extension.getTags();
        extension.setTags(new ArrayList<>());

        for (Tag tag: tags) {
            tag.setName(tagService.normalize(tag.getName()));
            Tag existingTag = tagService.findByName(tag.getName());
            if (existingTag != null) {
                extension.getTags().add(existingTag);
            }
            else {
                extension.getTags().add(tag);
            }
        }

        return extensionRepository.create(extension);
    }

    @Override
    public Extension findById(int id) {
        return extensionRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        extensionRepository.delete(id);
    }

    @Override
    public List<Extension> findByName(String searchQuery) {
        return extensionRepository.findByName(searchQuery);
    }

    @Override
    public List<Extension> findAll() {
        return extensionRepository.findAll();
    }
}
