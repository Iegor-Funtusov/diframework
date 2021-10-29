package org.diframework.core.searcher;

import org.reflections.Reflections;
import org.reflections.Store;

import java.util.*;
import java.util.stream.Collectors;

public class ApplicationSearcher {

    private final Reflections scanner;

    public ApplicationSearcher(String packageToScan) {
        this.scanner = new Reflections(packageToScan);
    }

    public <IFC> Set<Class<? extends IFC>> getAllImplementation(Class<IFC> ifc) {
        return scanner.getSubTypesOf(ifc);
    }

    public Set<Class<?>> findAllServiceInterfaces(ClassLoader appClassLoader, String rootPackage) {
        Store store = scanner.getStore();
        Collection<Map<String, Set<String>>> values = store.values();
        Set<String> allClassesName = new HashSet<>();
        values.forEach((v) -> {
            Set<String> packageClasses = v.keySet();
            allClassesName.addAll(packageClasses);
        });
        return allClassesName
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
