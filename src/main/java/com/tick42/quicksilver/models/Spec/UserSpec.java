package com.tick42.quicksilver.models.Spec;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserSpec {

    @NotNull(message="is required")
    @Size(min=1, message="is required")
    private String username;

    @NotNull(message="is required")
    @Size(min=1, message="is required")
    private String password;

    @NotNull(message="is required")
    @Size(min=1, message="is required")
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
