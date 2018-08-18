package com.tick42.quicksilver.services;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.repositories.base.ExtensionRepository;
import com.tick42.quicksilver.repositories.base.GenericRepository;
import com.tick42.quicksilver.services.base.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtensionsServiceImpl implements ExtensionService {

    private final ExtensionRepository extensionRepository;

    @Autowired
    public ExtensionsServiceImpl(ExtensionRepository extensionRepository) {
        this.extensionRepository = extensionRepository;
    }

    @Override
    public void create(Extension model) {
        extensionRepository.create(model);
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
