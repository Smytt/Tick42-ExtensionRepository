package com.tick42.quicksilver.models.DTO;

import com.tick42.quicksilver.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private int id;
    private String username;
    private int totalExtensions;
    private List<ExtensionDTO> extensions = new ArrayList<>();
    private boolean isActive;
    private double rating;
    private int extensions_rated;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setExtensions(
                user.getExtensions()
                        .stream()
                        .map(ExtensionDTO::new)
                        .collect(Collectors.toList()));
        this.setTotalExtensions(this.extensions.size());
        this.setIsActive(user.getIsActive());
        this.setExtensions_rated(user.getExtensionsRated());
        this.setRating(user.getRating());
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

    public List<ExtensionDTO> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<ExtensionDTO> extensions) {
        this.extensions = extensions;
    }

    public int getTotalExtensions() {
        return totalExtensions;
    }

    public void setTotalExtensions(int totalExtensions) {
        this.totalExtensions = totalExtensions;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getExtensions_rated() {
        return extensions_rated;
    }

    public void setExtensions_rated(int extensions_rated) {
        this.extensions_rated = extensions_rated;
    }
}
