package com.tick42.quicksilver.models.Spec;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GitHubSettingSpec {

    @NotNull(message = "Provide a GitHub username.")
    @Size(min = 1, message = "Username should be filled in.")
    private String username;

    @NotNull(message = "Provide a token, associated with the username")
    @Size(min = 1, message = "Token should be filled in.")
    private String token;

    @NotNull(message = "Enter github data refresh rate in milliseconds.")
    @Size(min = 1000, message = "The rate should be 1000 millisecond or more.")
    private Integer rate;

    @NotNull(message = "Enter waiting period in milliseconds before initial data fetch.")
    @Size(min=1000, message="Initial wait should be 1000 millisecond or more.")
    private Integer wait;

    public GitHubSettingSpec() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getWait() {
        return wait;
    }

    public void setWait(Integer wait) {
        this.wait = wait;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
