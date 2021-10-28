package org.diframework.core;

import org.diframework.core.factory.BeanFactory;
import org.diframework.core.factory.BeanStorage;
import org.diframework.core.searcher.ApplicationSearcher;
import org.diframework.core.util.ClassLoaderUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationContext {

    private BeanStorage beanStorage;
    private Set<Class<?>> serviceInterfaces;
    private BeanFactory beanFactory;
    private ApplicationSearcher applicationSearcher;
    private ClassLoader applicationClassLoader;

    public ApplicationContext(Class<?> mainClass) {
        try {
            Package rootPackage = mainClass.getPackage();
            applicationClassLoader = mainClass.getClassLoader();
            List<Class<?>> allClasses = ClassLoaderUtil.getAllClassesByPackage(rootPackage.getName(), applicationClassLoader);
            initServiceInterfaces(allClasses);
        } catch (Exception e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    public void initBeanMap() {
        this.serviceInterfaces.forEach(serviceInterface -> {
            Object impl = this.beanFactory.createBeanByInterface(serviceInterface);
            if (impl != null) {
                beanStorage.getBeanMap().put(serviceInterface, impl);
            }
        });
    }

    public void configureBeanMap() {
        beanStorage.getBeanMap().forEach((ifc, impl) -> {
            beanFactory.configureBean(impl);
        });
    }

    public void setApplicationSearcher(ApplicationSearcher applicationSearcher) {
        this.applicationSearcher = applicationSearcher;
    }

    public void setBeanStorage(BeanStorage beanStorage) {
        this.beanStorage = beanStorage;
        this.beanStorage.setApplicationClassLoader(this.applicationClassLoader);
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ClassLoader getApplicationClassLoader() {
        return applicationClassLoader;
    }

    private void initServiceInterfaces(List<Class<?>> allClasses) {
        this.serviceInterfaces = allClasses
                .stream()
                .filter(Class::isInterface)
                .filter(this::isServiceType)
                .filter(this::hasChildImpl)
                .collect(Collectors.toSet());
    }

    private boolean isServiceType(Class<?> interfaceClass) {
        return interfaceClass.getSimpleName().endsWith("Controller") ||
                interfaceClass.getSimpleName().endsWith("Service") ||
                interfaceClass.getSimpleName().endsWith("Dao");
    }

    private boolean hasChildImpl(Class<?> interfaceClass) {
        Set<Class<?>> classes = this.applicationSearcher.getAllImplementation((Class<Object>) interfaceClass);
        return classes.stream().noneMatch(Class::isInterface);
    }
}
