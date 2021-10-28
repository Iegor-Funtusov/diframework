package org.diframework.core.factory;

import java.util.HashMap;
import java.util.Map;

public class BeanStorage {

    private final Map<Class<?>, Object> beanMap = new HashMap<>();
    private ClassLoader applicationClassLoader;

    public Map<Class<?>, Object> getBeanMap() {
        return beanMap;
    }

    public ClassLoader getApplicationClassLoader() {
        return applicationClassLoader;
    }

    public void setApplicationClassLoader(ClassLoader applicationClassLoader) {
        this.applicationClassLoader = applicationClassLoader;
    }
}
