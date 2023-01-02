package io.erkki.encapsulationreport.core;

import java.util.Comparator;

/**
 * A jar file.
 */
public record Jar(String filename) implements Comparable<Jar> {
    @Override
    public int compareTo(Jar o) {
        return Comparator.comparing(Jar::filename)
                .thenComparing(Jar::filename).compare(this, o);
    }
}
