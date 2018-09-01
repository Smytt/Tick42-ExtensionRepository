package com.tick42.quicksilver.models.DTO;

import com.tick42.quicksilver.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private int id;
    private String username;
    private List<String> extensions = new ArrayList<>();
    private boolean isActive = true;
    private String role;

    public UserDTO(){

    }

    public UserDTO(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        user.getExtensions().forEach(extension -> this.extensions.add(extension.getName()));
        this.setActive(user.getIsActive());
        this.setRole(user.getRole());
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<String> extensions) {
        this.extensions = extensions;
    }
}
