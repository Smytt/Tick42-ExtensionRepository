package com.tick42.quicksilver.models.DTO;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Tag;
import com.tick42.quicksilver.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TagDTO {
    private String tag;
    private int totalExtensions;
    private List<ExtensionDTO> extensions = new ArrayList<>();

    public TagDTO() {

    }

    public TagDTO(Tag tag) {
        setTag(tag.getName());
        setTotalExtensions(tag.getExtensions().size());
        setExtensions(tag.getExtensions()
                .stream()
                .map(ExtensionDTO::new)
                .collect(Collectors.toList()));
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getTotalExtensions() {
        return totalExtensions;
    }

    public void setTotalExtensions(int totalExtensions) {
        this.totalExtensions = totalExtensions;
    }

    public List<ExtensionDTO> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<ExtensionDTO> extensions) {
        this.extensions = extensions;
    }
}
