package org.diframework.core.crud.entity;

import javax.persistence.Id;

public abstract class BaseEntity<ID> {

    @Id
    ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
