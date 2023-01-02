package io.erkki.encapsulationreport.classgraph;

import io.erkki.encapsulationreport.core.Dependency;
import io.erkki.encapsulationreport.core.Jar;
import io.erkki.encapsulationreport.core.Package;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

import java.util.List;
import java.util.stream.Stream;

/**
 * Parses {@link Dependency} objects from ClassGraph scan result.
 */
public class ClassGraphDependencyResolver {

    public static List<Dependency> resolve(String rootPackage, ScanResult scanResult) {
        return scanResult.getClassDependencyMap().entrySet().parallelStream()
                .filter(e -> e.getKey().getPackageName().startsWith(rootPackage))
                .flatMap(e -> resolveDependencies(e.getKey(), e.getValue().stream().toList()))
                .distinct()
                .sorted()
                .toList();
    }

    private static Stream<Dependency> resolveDependencies(ClassInfo clazz, List<ClassInfo> dependencies) {
        return dependencies.stream()
                .filter(ClassGraphDependencyResolver::isFromJar)
                .map(dependency -> asDependency(clazz, dependency))
                .distinct();
    }

    private static Dependency asDependency(ClassInfo clazz, ClassInfo dependency) {
        Package pkg = new Package(clazz.getPackageName());
        Jar jar = new Jar(getResourceName(dependency));
        return new Dependency(pkg, jar);
    }

    private static boolean isFromJar(ClassInfo dependency) {
        return dependency.getResource() != null && getResourceName(dependency).endsWith(".jar");
    }

    private static String getResourceName(ClassInfo dependency) {
        return dependency.getResource().getClasspathElementFile().getName();
    }
}
