package io.erkki.encapsulationreport.core;

import java.util.List;

/**
 * Packages in a hierarchical structure for {@link DependencyEncapsulationReport}.
 */
public record PackageHierarchyEntry(Package pkg, List<PackageHierarchyEntry> subpackages, List<Jar> dependencies) {
}
