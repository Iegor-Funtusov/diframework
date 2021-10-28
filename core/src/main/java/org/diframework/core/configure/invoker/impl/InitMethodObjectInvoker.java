package org.diframework.core.configure.invoker.impl;

import org.diframework.core.annotations.InitMethod;
import org.diframework.core.configure.invoker.ObjectInvoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InitMethodObjectInvoker implements ObjectInvoker {

    @Override
    public void invoke(Object bean) {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(InitMethod.class)) {
                method.setAccessible(true);
                try {
                    method.invoke(bean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
