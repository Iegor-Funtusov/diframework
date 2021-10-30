package org.diframework.core.crud.starter.console;

import java.lang.reflect.Method;
import java.util.Map;

public class ConsoleControllerHelper {

    private final Map<Integer, Method> methods;
    private final Object controller;

    public ConsoleControllerHelper(Map<Integer, Method> methods, Object controller) {
        this.methods = methods;
        this.controller = controller;
    }

    public Map<Integer, Method> getMethods() {
        return methods;
    }

    public Object getController() {
        return controller;
    }
}
