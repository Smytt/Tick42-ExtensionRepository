package com.tick42.quicksilver.models;

import com.tick42.quicksilver.models.Spec.ExtensionSpec;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "extensions")
public class Extension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private File file;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private File image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "github_id")
    private GitHubModel github;

    @Column(name = "times_downloaded")
    private int timesDownloaded;

    @Column(name = "version")
    private String version;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "extension_tags",
            joinColumns = @JoinColumn(name = "extension_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner")
    private User owner;

    @Column(name = "is_pending")
    private boolean isPending;

    @Column(name = "upload_date")
    private Date uploadDate;

    @Column(name = "is_featured")
    private boolean isFeatured;

    @Column(name = "rating")
    private double rating;

    @Column(name = "times_rated")
    private int timesRated;

    public Extension() {

    }

    public Extension(ExtensionSpec extensionSpec) {
        this.setName(extensionSpec.getName());
        this.setVersion(extensionSpec.getVersion());
        this.setDescription(extensionSpec.getDescription());
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

    public int getTimesDownloaded() {
        return timesDownloaded;
    }

    public void setTimesDownloaded(int timesDownloaded) {
        this.timesDownloaded = timesDownloaded;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean getIsPending() {
        return isPending;
    }

    public void setIsPending(boolean pending) {
        isPending = pending;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(boolean featured) {
        isFeatured = featured;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public GitHubModel getGithub() {
        return github;
    }

    public void setGithub(GitHubModel github) {
        this.github = github;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTimesRated() {
        return timesRated;
    }

    public void setTimesRated(int timesRated) {
        this.timesRated = timesRated;
    }
}
