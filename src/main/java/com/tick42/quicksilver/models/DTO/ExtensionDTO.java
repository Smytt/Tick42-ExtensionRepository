package com.tick42.quicksilver.models.DTO;

import com.tick42.quicksilver.models.Extension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExtensionDTO {
    private int id;
    private String name;
    private String version;
    private String description;
    private int timesDownloaded;
    private boolean isPending;
    private boolean isFeatured;
    private String uploadDate;
    private String ownerName;
    private int ownerId;
    private String gitHubLink;
    private String lastCommit;
    private int openIssues;
    private int pullRequests;
    private String fileLocation;
    private String imageLocation;
    private List<String> tags = new ArrayList<>();

    public ExtensionDTO() {

    }

    public ExtensionDTO(Extension extension) {
        this.setId(extension.getId());
        this.setName(extension.getName());
        this.setDescription(extension.getDescription());
        this.setFeatured(extension.getIsFeatured());
        if (extension.getGithub() != null) {
            this.setGitHubLink(extension.getGithub().getLink());
            if (extension.getGithub().getLastCommit() != null) {
                this.setLastCommit(extension.getGithub().getLastCommit());
            }
            this.setOpenIssues(extension.getGithub().getOpenIssues());
            this.setPullRequests(extension.getGithub().getPullRequests());
        }
        if (extension.getImage() != null) {
            this.setImageLocation(extension.getImage().getLocation());
        }
        if (extension.getFile() != null) {
            this.setFileLocation(extension.getFile().getLocation());
        }
        if (extension.getOwner() != null) {
            this.setOwnerId(extension.getOwner().getId());
            this.setOwnerName(extension.getOwner().getUsername());
        }
        this.setPending(extension.getIsPending());
        extension.getTags().forEach(tag -> this.tags.add(tag.getName()));
        this.setTimesDownloaded(extension.getTimesDownloaded());
        if (extension.getUploadDate() != null) {
            this.setUploadDate(extension.getUploadDate());
        }
        this.setVersion(extension.getVersion());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getTimesDownloaded() {
        return timesDownloaded;
    }

    public void setTimesDownloaded(int timesDownloaded) {
        this.timesDownloaded = timesDownloaded;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate.toString();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getGitHubLink() {
        return gitHubLink;
    }

    public void setGitHubLink(String gitHubLink) {
        this.gitHubLink = gitHubLink;
    }

    public String getLastCommit() {
        return lastCommit;
    }

    public void setLastCommit(Date lastCommit) {
        this.lastCommit = lastCommit.toString();
    }

    public int getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(int openIssues) {
        this.openIssues = openIssues;
    }

    public int getPullRequests() {
        return pullRequests;
    }

    public void setPullRequests(int pullRequests) {
        this.pullRequests = pullRequests;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
