package org.diframework.core.searcher;

import org.reflections.Reflections;

public class DiframeworkSearcher {

    private final Reflections scanner;

    public DiframeworkSearcher(String packageToScan) {
        this.scanner = new Reflections(packageToScan);
    }

    public Reflections getScanner() {
        return scanner;
    }
}
