package org.diframework.core.storage;

import org.diframework.core.util.ResourcesUtil;

import java.util.HashMap;
import java.util.Map;

public final class PropertiesStorage {

    private static PropertiesStorage instance;
    private Map<String, String> properties;

    private PropertiesStorage() {
        this.properties = new HashMap<>();
    }

    public static PropertiesStorage getInstance() {
        if (instance == null) {
            instance = new PropertiesStorage();
        }
        return instance;
    }

    public void initPropertiesStorage(ClassLoader classLoader) {
        this.properties = ResourcesUtil.getProperties(classLoader);
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
