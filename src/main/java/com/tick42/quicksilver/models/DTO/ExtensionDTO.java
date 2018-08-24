package com.tick42.quicksilver.models.DTO;

import com.tick42.quicksilver.models.Extension;

import javax.persistence.Entity;

public class ExtensionDTO {

    private String name;
    private String version;
    private String description;
    private String github;
    private String tags;

    public ExtensionDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
