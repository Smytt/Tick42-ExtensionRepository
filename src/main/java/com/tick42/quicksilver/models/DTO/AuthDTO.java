package com.tick42.quicksilver.models.DTO;

import com.tick42.quicksilver.models.User;

public class AuthDTO {
    private String token;
    private int id;
    private String username;
    private String role;

    public AuthDTO() {

    }

    public AuthDTO(User user, String token) {
        this.setId(user.getId());
        this.setToken(token);
        this.setUsername(user.getUsername());
        this.setRole(user.getRole());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
