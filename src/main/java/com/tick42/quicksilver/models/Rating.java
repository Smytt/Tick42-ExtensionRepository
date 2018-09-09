package com.tick42.quicksilver.models;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@IdClass(RatingPK.class)
@Table(name = "ratings")
public class Rating implements Serializable {

    @Column(name = "rating")
    private int rating;

    @Id
    private int extension;

    @Id
    private int user;

    public Rating() {
    }

    public Rating(int rating, int extension, int user) {
        this.rating = rating;
        this.extension = extension;
        this.user = user;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getExtension() {
        return extension;
    }

    public void setExtension(int extension) {
        this.extension = extension;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
