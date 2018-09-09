package com.tick42.quicksilver.services.base;

import com.tick42.quicksilver.models.DTO.TagDTO;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Tag;

import java.util.List;
import java.util.Set;


public interface TagService {

    TagDTO findByName(String name);

    String normalize(String name);

    List<Tag> prepareTags(List<Tag> tags);

    Set<Tag> generateTags(String tags);
}
