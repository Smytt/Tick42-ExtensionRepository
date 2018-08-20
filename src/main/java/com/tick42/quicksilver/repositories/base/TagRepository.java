package com.tick42.quicksilver.repositories.base;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Tag;

import java.util.List;

public interface TagRepository extends GenericRepository<Tag> {
    Tag findByName(String name);
}
