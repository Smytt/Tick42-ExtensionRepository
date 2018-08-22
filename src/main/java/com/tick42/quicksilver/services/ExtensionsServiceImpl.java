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
        extension.setTags(tagService.prepareTags(extension.getTags()));

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

    @Override
    public List<Extension> findTopMostDownloaded(int count) {
        return extensionRepository.findTopMostDownloaded(count);
    }

    @Override
    public List<Extension> findMostRecentUploads(int count) {
        return extensionRepository.findMostRecentUploads(count);
    }

    @Override
    public List<Extension> findFeatured(int count) {
        return extensionRepository.findFeatured(count);
    }

    @Override
    public List<Extension> sortByUploadDate() {
        return extensionRepository.sortByUploadDate();
    }

    @Override
    public List<Extension> sortByMostDownloads() {
        return extensionRepository.sortByMostDownloads();
    }

    @Override
    public List<Extension> sortByCommitDate() {
        return extensionRepository.sortByCommitDate();
    }

    @Override
    public List<Extension> findByTag(String tagName) {
        return tagService.findByName(tagName).getExtensions();
    }

    @Override
    public void approveExtension(int id) {
        Extension extension = extensionRepository.findById(id);
        extension.setIsPending(false);
        extensionRepository.update(extension);
    }

    @Override
    public void changeFeaturedState(int id){
        Extension extension = extensionRepository.findById(id);
        if (extension.getIsFeatured()){
            extension.setIsFeatured(false);
            extensionRepository.update(extension);
        }else {
            extension.setIsFeatured(true);
            extensionRepository.update(extension);
        }
    }
}
