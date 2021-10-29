package org.diframework.core.crud.starter.console;

import org.diframework.core.factory.BeanStorage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.Collectors;

public class ApplicationConsoleStarter {

    private final Collection<Object> controllers;

    public ApplicationConsoleStarter(BeanStorage beanStorage) {
        this.controllers = beanStorage.getBeanMap().values().stream().filter(obj -> obj.getClass().getName().endsWith("Controller")).collect(Collectors.toSet());
    }

    public void start() {
        this.controllers.forEach(controller -> {
            Method[] methods = controller.getClass().getMethods();
            for (Method method : methods) {
                if (method.getName().equals("run")) {
                    try {
                        method.invoke(controller);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
