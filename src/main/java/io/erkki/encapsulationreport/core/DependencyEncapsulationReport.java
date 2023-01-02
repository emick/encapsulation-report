package io.erkki.encapsulationreport.core;

import java.util.List;
import java.util.stream.Stream;

/**
 * Result of dependency encapsulation analysis.
 */
public record DependencyEncapsulationReport(List<PackageHierarchyEntry> rootPackages) {

    public List<Dependency> asDependencies() {
        return rootPackages.stream()
                // Flatten entry hierarchy
                .flatMap(this::entries)
                .flatMap(entry -> entry.dependencies().stream().map(dep -> new Dependency(entry.pkg(), dep)))
                .toList();
    }

    private Stream<PackageHierarchyEntry> entries(PackageHierarchyEntry entry) {
        Stream<PackageHierarchyEntry> currentEntry = Stream.of(entry);
        Stream<PackageHierarchyEntry> subpackageEntries = entry.subpackages().stream().flatMap(this::entries);
        return Stream.concat(currentEntry, subpackageEntries);
    }

}
