package com.tick42.quicksilver.services.base;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Tag;

import java.util.List;


public interface TagService {

    Tag create(Tag model);

    Tag findById(int id);

    Tag findByName(String name);

    List<Extension> findExtensionsByTag(String tagName);

    String normalize(String name);

    List<Tag> prepareTags(List<Tag> tags);
}
