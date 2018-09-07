package com.tick42.quicksilver.models.Spec;


import com.tick42.quicksilver.validation.ValidPassword;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserSpec {

    @NotNull
    @Size(min=7, max=22, message="Name should be be between 7 and 18 char.")
    private String username;

    @NotNull
    private String password;

    @NotNull
    @ValidPassword
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
