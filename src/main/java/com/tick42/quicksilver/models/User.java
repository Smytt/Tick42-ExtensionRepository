package com.tick42.quicksilver.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "owner")
    private List<Extension> extensions = new ArrayList<>();

    @Column(name = "enabled", nullable = false) 
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isActive = true;

    public User() {

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
}
