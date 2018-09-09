package com.tick42.quicksilver.models;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private Set<Extension> extensions = new HashSet<>();

    public Tag() {

    }

    public Tag(String name) {
        this.setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Extension> getExtensions() {
        return extensions;
    }

    public void setExtensions(Set<Extension> extensions) {
        this.extensions = extensions;
    }

    @Override
    public String toString() {
        return name;
    }
}
