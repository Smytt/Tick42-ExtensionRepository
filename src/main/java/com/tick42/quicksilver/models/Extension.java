package com.tick42.quicksilver.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tick42.quicksilver.serializers.ExtensionSerializer;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "extensions")
@JsonSerialize(using = ExtensionSerializer.class)
public class Extension {

    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "download_link")
    private String downloadLink;

    @Column(name = "github")
    private String github;

    @Column(name = "times_downloaded")
    private int timesDownloaded;

    @Column(name = "version")
    private double version;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "extension_tags",
            joinColumns = @JoinColumn(name = "extension_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner")
    private User owner;

    @Column(name = "is_pending", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isPending = true;

    @Column(name = "last_commit")
    private Date lastCommit;

    @Column(name = "pull_requests")
    private int pullRequests;

    @Column(name = "open_issues")
    private int openIssues;


    public Extension() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public int getTimesDownloaded() {
        return timesDownloaded;
    }

    public void setTimesDownloaded(int timesDownloaded) {
        this.timesDownloaded = timesDownloaded;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    public Date getLastCommit() {
        return lastCommit;
    }

    public void setLastCommit(Date lastCommit) {
        this.lastCommit = lastCommit;
    }

    public int getPullRequests() {
        return pullRequests;
    }

    public void setPullRequests(int pullRequests) {
        this.pullRequests = pullRequests;
    }

    public int getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(int openIssues) {
        this.openIssues = openIssues;
    }
}
