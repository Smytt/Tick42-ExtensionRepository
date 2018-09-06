
package com.tick42.quicksilver.models;

import java.io.Serializable;
import java.util.Objects;

public class RatingPK implements Serializable {

    protected int extension;

    protected int user;

    public RatingPK() {
    }

    public RatingPK(int extension, int user) {
        this.extension = extension;
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatingPK)) return false;
        RatingPK that = (RatingPK) o;
        return Objects.equals(getExtension(), that.getExtension()) &&
                Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getExtension());
    }

    public void setUser(int user) {
        this.user = user;
    }
}
