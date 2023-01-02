package io.erkki.encapsulationreport.core;

import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * A Java package.
 */
public record Package(String name) implements Comparable<Package> {
    @Override
    public int compareTo(Package o) {
        return Comparator.comparing(Package::name)
                .thenComparing(Package::name).compare(this, o);
    }

    /**
     * @return base package of the class. E.g. "com" for class "com.example.app"
     */
    public Package rootPackage() {
        return new Package(name.split(Pattern.quote("."))[0]);
    }

    public boolean isSubpackageOf(Package pkg) {
        return name.startsWith(pkg.name + ".");
    }
}
