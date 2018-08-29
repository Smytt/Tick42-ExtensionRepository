package com.tick42.quicksilver.models.Spec;

import com.tick42.quicksilver.models.Extension;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ExtensionSpec {

    @NotNull
    @Size(min=1, message="Name should be at least 1 character.")
    private String name;


    @NotNull
    @Size(min=1, message="Version should be at least 1 character.")
    private String version;

    @NotNull
    @Size(min=1, message="Description should be at least 1 character.")
    private String description;

    @NotNull
    @Pattern(regexp = "^https://github.com/.+/.+$", message = "Link to github should match https://github.com/USER/REPOSITORY")
    private String github;

    private String tags;

    public ExtensionSpec() {
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
