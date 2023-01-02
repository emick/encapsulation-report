package io.erkki.encapsulationreport.core;

import java.util.List;
import java.util.regex.Pattern;

public class DependencyEncapsulationReporter {

    public DependencyEncapsulationReport report(List<Dependency> dependencies) {
        List<Package> packages = dependencies.stream()
                .map(Dependency::pkg)
                .distinct()
                .toList();

        List<PackageHierarchyEntry> hierarchy = dependencies.stream()
                .map(dependency -> dependency.pkg().rootPackage())
                .distinct()
                .map(rootPackage -> asHierarchy(rootPackage, packages, dependencies))
                .toList();

        return new DependencyEncapsulationReport(hierarchy);
    }

    private PackageHierarchyEntry asHierarchy(Package pkg, List<Package> allPackages, List<Dependency> allDependencies) {
        // This method is inefficient, but since no. of packages is small, it should be fine

        List<Jar> currentDependencies = allDependencies.stream()
                .filter(dependency -> dependency.pkg().equals(pkg))
                .map(Dependency::jar)
                .toList();

        List<PackageHierarchyEntry> subpackages = getDirectSubpackages(pkg, allPackages).stream()
                .filter(subpackage -> subpackage.isSubpackageOf(pkg))
                .map(subpackage -> asHierarchy(subpackage, allPackages, allDependencies))
                .toList();

        return new PackageHierarchyEntry(pkg, subpackages, currentDependencies);
    }

    private List<Package> getDirectSubpackages(Package parent, List<Package> allPackages) {
        return allPackages.stream()
                .filter(p -> p.isSubpackageOf(parent))
                .map(subpackage -> getDirectSubpackage(parent, subpackage))
                .distinct()
                .toList();
    }

    /**
     * @return the subpackage between given parent package and subpackage, or subpackage if there is no packages between
     * the parent and subpackage.
     */
    private Package getDirectSubpackage(Package parent, Package subpackage) {
        String packagesAfterParent = subpackage.name().split(Pattern.quote(parent.name() + "."))[1];
        String directSubpackageAfterParent = packagesAfterParent.split(Pattern.quote("."))[0];
        return new Package(parent.name() + "." + directSubpackageAfterParent);
    }
}
