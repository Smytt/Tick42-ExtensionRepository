package com.tick42.quicksilver.models;

import javax.persistence.*;

@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "github_refresh")
    private int gitHubRefresh;

    @Column(name = "initial_wait")
    private int initialWait;

    @Column(name = "enable_sync")
    private boolean enableSync;

    public Settings() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGitHubRefresh() {
        return gitHubRefresh;
    }

    public void setGitHubRefresh(int gitHubRefresh) {
        this.gitHubRefresh = gitHubRefresh;
    }

    public int getInitialWait() {
        return initialWait;
    }

    public void setInitialWait(int initialWait) {
        this.initialWait = initialWait;
    }

    public boolean getEnableSync() {
        return enableSync;
    }

    public void setEnableSync(boolean enableSync) {
        this.enableSync = enableSync;
    }
}
