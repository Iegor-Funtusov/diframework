package org.diframework.core.storage;

import java.util.HashSet;
import java.util.Set;

public final class EntityStorage {

    private static EntityStorage instance;
    private final Set<Class<?>> entities;

    private EntityStorage() {
        this.entities = new HashSet<>();
    }

    public static EntityStorage getInstance() {
        if (instance == null) {
            instance = new EntityStorage();
        }
        return instance;
    }

    public Set<Class<?>> getEntities() {
        return entities;
    }

    public void initEntities() {

    }
}
