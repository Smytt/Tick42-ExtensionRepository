package com.tick42.quicksilver.services;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.repositories.ExtensionRepositoryImpl;
import com.tick42.quicksilver.repositories.base.GenericRepository;
import com.tick42.quicksilver.services.base.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtensionsServiceImpl implements ExtensionService {

    private final GenericRepository<Extension> extensionRepository;

    @Autowired
    public ExtensionsServiceImpl(ExtensionRepositoryImpl genericRepository) {
        this.extensionRepository = genericRepository;
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
}
