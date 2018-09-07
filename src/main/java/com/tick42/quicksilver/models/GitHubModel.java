package com.tick42.quicksilver.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "github")
public class GitHubModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "link")
    private String link;

    @Column(name = "user")
    private String user;

    @Column(name = "repo")
    private String repo;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Extension extension;

    @Column(name = "last_commit")
    private Date lastCommit;

    @Column(name = "pull_requests")
    private int pullRequests;

    @Column(name = "open_issues")
    private int openIssues;

    @Column(name = "last_success")
    private Date lastSuccess;

    @Column(name = "last_fail")
    private Date lastFail;

    @Column(name = "fail_msg")
    private String failMessage;

    public GitHubModel() {

    }

    public GitHubModel(String link, String user, String repo) {
        this.setLink(link);
        this.setUser(user);
        this.setRepo(repo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
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

    public Date getLastSuccess() {
        return lastSuccess;
    }

    public void setLastSuccess(Date lastSuccess) {
        this.lastSuccess = lastSuccess;
    }

    public Date getLastFail() {
        return lastFail;
    }

    public void setLastFail(Date lastFail) {
        this.lastFail = lastFail;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }
}
