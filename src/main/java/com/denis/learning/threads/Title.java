package com.denis.learning.threads;

import java.util.Objects;

public class Title {

    private int id;

    public Title(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Title title = (Title) o;
        return id == title.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Title{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
