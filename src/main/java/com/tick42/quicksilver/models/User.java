package com.tick42.quicksilver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    private int id;

    @NotNull(message="is required")
    @Size(min=1, message="is required")
    @Column(name = "username")
    private String username;

    @NotNull(message="is required")
    @Size(min=1, message="is required")
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Extension> extensions = new ArrayList<>();

    @Column(name = "enabled", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isActive = true;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Extension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
