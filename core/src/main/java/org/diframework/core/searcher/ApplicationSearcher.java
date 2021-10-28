package org.diframework.core.searcher;

import org.reflections.Reflections;

import java.util.Set;

public class ApplicationSearcher {

    private final Reflections scanner;

    public ApplicationSearcher(String packageToScan) {
        this.scanner = new Reflections(packageToScan);
    }

    public <IFC> Set<Class<? extends IFC>> getAllImplementation(Class<IFC> ifc) {
        return scanner.getSubTypesOf(ifc);
    }

    public Reflections getScanner() {
        return scanner;
    }
}
