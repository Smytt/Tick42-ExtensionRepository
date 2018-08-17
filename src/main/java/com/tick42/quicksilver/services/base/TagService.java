package com.tick42.quicksilver.services.base;

import com.tick42.quicksilver.models.Tag;


public interface TagService {

    void create(Tag model);

    Tag findById(int id);

    void delete(int id);
}
