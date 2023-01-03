package io.erkki.encapsulationreport.run;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

abstract class CommandLineApp {

    protected static final String ROOT_PACKAGE_PROPERTY = "rootPackage";
    protected static final String EXCLUSIONS_PROPERTY = "exclude";

    protected static String parseProperty(String property) {
        String value = System.getProperty(property);
        if (value == null) {
            throw new RuntimeException("Program argument '" + property + "' is not set");
        }

        return value;
    }

    protected static Optional<String> parseOptionalProperty(String property) {
        return Optional.ofNullable(System.getProperty(property));
    }

    protected static List<String> parseExclusions(String exclusionsArgument) {
        if (exclusionsArgument == null || exclusionsArgument.isBlank()) {
            return Collections.emptyList();
        }

        return Arrays.asList(exclusionsArgument.split("\\|"));
    }
}
