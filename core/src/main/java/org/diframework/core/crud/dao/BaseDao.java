package org.diframework.core.crud.dao;

import org.diframework.core.crud.entity.BaseEntity;

import java.util.Collection;

public interface BaseDao<ID, ENTITY extends BaseEntity<ID>> {

    void create(ENTITY entity);
    void update(ENTITY entity);
    void delete(ID id);
    ENTITY findById(ID id);
    Collection<ENTITY> findAll();
}
