package org.diframework.core.storage;

import java.util.HashMap;
import java.util.Map;

public final class BeanStorage {

    private static BeanStorage instance;
    private final Map<Class<?>, Object> beanMap;

    private BeanStorage() {
        beanMap = new HashMap<>();
    }

    public static BeanStorage getInstance() {
        if (instance == null) {
            instance = new BeanStorage();
        }
        return instance;
    }

    public Map<Class<?>, Object> getBeanMap() {
        return beanMap;
    }
}
