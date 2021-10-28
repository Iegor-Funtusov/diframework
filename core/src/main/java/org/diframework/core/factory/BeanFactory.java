package org.diframework.core.factory;

import org.diframework.core.annotations.Service;
import org.diframework.core.configure.configurator.ObjectConfigurator;
import org.diframework.core.configure.invoker.ObjectInvoker;
import org.diframework.core.searcher.ApplicationSearcher;
import org.diframework.core.searcher.DiframeworkSearcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BeanFactory {

    private final ApplicationSearcher applicationSearcher;
    private final DiframeworkSearcher diframeworkSearcher;
    private final BeanStorage beanStorage;
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();
    private final List<ObjectInvoker> objectInvokers = new ArrayList<>();

    public BeanFactory(
            final ApplicationSearcher applicationSearcher,
            final BeanStorage beanStorage,
            final DiframeworkSearcher diframeworkSearcher) {
        this.applicationSearcher = applicationSearcher;
        this.beanStorage = beanStorage;
        this.diframeworkSearcher = diframeworkSearcher;
        initObjectConfiguratorList();
        initObjectInvokerList();
    }

    public <IFC> IFC createBeanByInterface(Class<IFC> ifc) {
        if (ifc.isInterface()) {
            Set<Class<? extends IFC>> implementations = applicationSearcher.getScanner().getSubTypesOf(ifc);
            for (Class<? extends IFC> implementation : implementations) {
                if (implementation.isAnnotationPresent(Service.class)) {
                    try {
                        return implementation.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    private <I> void configureBean(I i) {
        objectConfigurators.forEach(objectConfigurator -> objectConfigurator.configure(i, context));
    }

    private <I> void invokeBean(Class<I> type, I i) {
        objectInvokers.forEach(objectInvoker -> objectInvoker.invoke(type, i));
    }

    private void initObjectConfiguratorList() {
        Set<Class<? extends ObjectConfigurator>> subTypesOfObjectConfigurator =
                diframeworkSearcher.getScanner().getSubTypesOf(ObjectConfigurator.class);
        for (Class<? extends ObjectConfigurator> aClass : subTypesOfObjectConfigurator) {
            try {
                objectConfigurators.add(aClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private void initObjectInvokerList() {
        Set<Class<? extends ObjectInvoker>> subTypesOfObjectInvoker =
                diframeworkSearcher.getScanner().getSubTypesOf(ObjectInvoker.class);
        for (Class<? extends ObjectInvoker> aClass : subTypesOfObjectInvoker) {
            try {
                objectInvokers.add(aClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
