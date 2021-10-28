package org.diframework.core.configure.configurator.impl;

import org.apache.commons.lang3.StringUtils;
import org.diframework.core.ApplicationContext;
import org.diframework.core.annotations.Value;
import org.diframework.core.configure.configurator.ObjectConfigurator;
import org.diframework.core.factory.BeanStorage;
import org.diframework.core.util.ResourcesUtil;

import java.lang.reflect.Field;
import java.util.Map;

public class ValueAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    public void configure(Object bean, BeanStorage beanStorage) {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        Map<String, String> map = ResourcesUtil.getProperties(context.getApplicationClassLoader());
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Value.class)) {
                Value resourceProperty = declaredField.getAnnotation(Value.class);
                String value = resourceProperty.value();
                String props = map.get(value);
                if (StringUtils.isNotBlank(props)) {
                    declaredField.setAccessible(true);
                    try {
                        declaredField.set(bean, props);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("problem from initial field");
                    }
                }
            }
        }
    }
}
