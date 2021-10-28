package org.diframework.core.configure.configurator;

import org.diframework.core.factory.BeanStorage;

public interface ObjectConfigurator {

    void configure(Object bean, BeanStorage beanStorage);
}
