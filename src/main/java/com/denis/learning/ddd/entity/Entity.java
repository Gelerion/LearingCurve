package com.denis.learning.ddd.entity;

import java.util.Objects;

/**
 * Created by denis.shuvalov on 20/02/2018.
 */
public abstract class Entity<ID> {

    private ID id;

    public Entity(ID id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
