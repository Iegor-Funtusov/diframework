package org.diframework.core.factory;

import java.util.HashMap;
import java.util.Map;

public class BeanStorage {

    private final Map<Class<?>, Object> beanMap = new HashMap<>();

    public Map<Class<?>, Object> getBeanMap() {
        return beanMap;
    }
}
