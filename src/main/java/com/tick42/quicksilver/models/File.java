package com.tick42.quicksilver.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class File {
    @Id
    private int id;

    public File() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
