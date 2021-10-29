package org.diframework.core.crud.entity;

public abstract class BaseEntity<ID> {

    ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
