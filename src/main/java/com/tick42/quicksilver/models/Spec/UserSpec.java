package com.tick42.quicksilver.models.Spec;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserSpec {
    @NotNull
    @Size(min=7, message="Name should be at least 7 character.")
    private String username;

    @NotNull
    @Size(min=7, message="Name should be at least 7 character.")
    private String password;

    @NotNull
    @Size(min=7, message="pass should be at least 7 character.")
    private String repeatPassword;

    public UserSpec() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
