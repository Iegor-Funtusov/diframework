package org.diframework.core;

import org.diframework.core.factory.BeanFactory;
import org.diframework.core.storage.BeanStorage;
import org.diframework.core.searcher.ApplicationSearcher;

import java.util.Set;

public class ApplicationContext {

    private Set<Class<?>> serviceInterfaces;
    private BeanFactory beanFactory;
    private ApplicationSearcher applicationSearcher;
    private ClassLoader applicationClassLoader;
    private String rootPackage;

    public ApplicationContext(Class<?> mainClass) {
        try {
            applicationClassLoader = mainClass.getClassLoader();
            rootPackage = mainClass.getPackageName();
        } catch (Exception e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    public void initServiceInterfaces() {
        this.serviceInterfaces = applicationSearcher.getServiceInterfaces();
    }

    public void initBeanMap() {
        this.serviceInterfaces.forEach(serviceInterface -> {
            Object impl = this.beanFactory.createBeanByInterface(serviceInterface);
            if (impl != null) {
                BeanStorage.getInstance().getBeanMap().put(serviceInterface, impl);
            }
        });
    }

    public void configureBeanMap() {
        BeanStorage.getInstance().getBeanMap().forEach((ifc, impl) -> {
            beanFactory.configureBean(impl);
        });
    }

    public void setApplicationSearcher(ApplicationSearcher applicationSearcher) {
        this.applicationSearcher = applicationSearcher;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ClassLoader getApplicationClassLoader() {
        return applicationClassLoader;
    }

    public String getRootPackage() {
        return rootPackage;
    }
}
