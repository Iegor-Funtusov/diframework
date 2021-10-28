package org.diframework.core.configure.configurator.impl;

import org.diframework.core.ApplicationContext;
import org.diframework.core.annotations.Service;
import org.diframework.core.configure.configurator.ObjectConfigurator;
import org.diframework.core.factory.BeanStorage;

import java.lang.reflect.Field;

public class AutowiredAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    public void configure(Object bean, BeanStorage beanStorage) {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Service.class)) {
                declaredField.setAccessible(true);
                Object impl = beanStorage.getBeanMap().get(declaredField.getType());
                try {
                    declaredField.set(bean, impl);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("problem from initial field");
                }
            }
        }
    }
}
