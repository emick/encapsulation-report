package io.erkki.encapsulationreport.classgraph;

import io.erkki.encapsulationreport.core.Dependency;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.util.List;

/**
 * A preconfigured ClassGraph library based scanner to find dependencies between packages and jars.
 */
public class ClassGraphDependencyScanner {

    public static List<Dependency> scan(String rootPackage, List<String> exclusions) {
        try (ScanResult scanResult = new ClassGraph()
                .enableInterClassDependencies()
                .enableExternalClasses()
                // Note: scan must not be limited to rootPackage, because then it will not find all jars
                .scan()) {

            return ClassGraphDependencyResolver.resolve(rootPackage, scanResult, exclusions);
        }
    }
}
