package com.tick42.quicksilver.services;

import com.tick42.quicksilver.models.Tag;
import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.repositories.TagRepositoryImpl;
import com.tick42.quicksilver.repositories.base.GenericRepository;
import com.tick42.quicksilver.services.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {
    private final GenericRepository<Tag> tagRepository;

    @Autowired
    public TagServiceImpl (TagRepositoryImpl genericRepository){
        this.tagRepository = genericRepository;
    }

    @Override
    public void create(Tag model) {
        tagRepository.create(model);
    }

    @Override
    public Tag findById(int id) {
        return tagRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        tagRepository.delete(id);
    }

}
