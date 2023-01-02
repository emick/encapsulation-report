package io.erkki.encapsulationreport.core;

import java.util.Comparator;

/**
 * A dependency between a package and a jar.
 */
public record Dependency(Package pkg, Jar jar) implements Comparable<Dependency> {
    @Override
    public int compareTo(Dependency o) {
        return Comparator.comparing(Dependency::pkg)
                .thenComparing(Dependency::jar).compare(this, o);
    }
}