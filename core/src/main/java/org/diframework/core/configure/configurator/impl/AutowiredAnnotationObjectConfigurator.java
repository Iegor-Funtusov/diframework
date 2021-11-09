package org.diframework.core.configure.configurator.impl;

import org.diframework.core.annotations.Autowired;
import org.diframework.core.configure.configurator.ObjectConfigurator;
import org.diframework.core.storage.BeanStorage;

import java.lang.reflect.Field;

public class AutowiredAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    public void configure(Object bean) {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Autowired.class)) {
                declaredField.setAccessible(true);
                Object impl = BeanStorage.getInstance().getBeanMap().get(declaredField.getType());
                try {
                    declaredField.set(bean, impl);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("problem from initial field");
                }
            }
        }
    }
}
