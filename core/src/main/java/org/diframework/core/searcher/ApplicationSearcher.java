package org.diframework.core.searcher;

import org.diframework.core.storage.EntityStorage;
import org.reflections.Reflections;
import org.reflections.Store;

import javax.persistence.Entity;
import java.util.*;
import java.util.stream.Collectors;

public class ApplicationSearcher {

    private final Reflections scanner;
    private Set<Class<?>> serviceInterfaces;

    public ApplicationSearcher(Class<?> mainClass) {
        this.scanner = new Reflections(mainClass.getPackageName());
        initContext(mainClass);
    }

    public <IFC> Set<Class<? extends IFC>> getAllImplementation(Class<IFC> ifc) {
        return scanner.getSubTypesOf(ifc);
    }

    private void initContext(Class<?> mainClass) {
        findAllServiceInterfaces(mainClass.getClassLoader(), mainClass.getPackageName());
    }

    public Set<Class<?>> getServiceInterfaces() {
        return serviceInterfaces;
    }

    private void findAllServiceInterfaces(ClassLoader appClassLoader, String rootPackage) {
        Store store = scanner.getStore();
        Collection<Map<String, Set<String>>> values = store.values();
        Set<String> allInterfacesName = new HashSet<>();
        values.forEach((v) -> {
            v.forEach((key, value) -> {
                if (Entity.class.getName().equals(key)) {
                    value.forEach(entity -> {
                        EntityStorage.getInstance().getEntities().add(scanner.forClass(entity, appClassLoader));
                    });
                }
            });
            Set<String> packageClasses = v.keySet();
            allInterfacesName.addAll(packageClasses);
        });

        this.serviceInterfaces = allInterfacesName
                .stream()
                .filter(this::isServiceInterface)
                .filter(s -> isStartWithRootPackage(s, rootPackage))
                .map(className -> scanner.forClass(className, appClassLoader))
                .filter(this::hasOnlyChildClass)
                .collect(Collectors.toSet());
    }

    private boolean isServiceInterface(String className) {
        return
                className.endsWith("Dao") ||
                className.endsWith("Service") ||
                className.endsWith("Controller") ||
                className.endsWith("Facade");
    }

    private boolean isStartWithRootPackage(String className, String rootPackage) {
        return className.startsWith(rootPackage);
    }

    private boolean hasOnlyChildClass(Class<?> interfaceClass) {
        Set<Class<?>> classes = scanner.getSubTypesOf((Class<Object>) interfaceClass);
        return classes.stream().noneMatch(Class::isInterface);
    }

    public Reflections getScanner() {
        return scanner;
    }
}
