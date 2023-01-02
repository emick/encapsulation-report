package io.erkki.encapsulationreport.run;

import io.erkki.encapsulationreport.EncapsulationReport;

import java.io.File;

public class PngOutMain {

    public static final String ROOT_PACKAGE_PROPERTY = "rootPackage";
    public static final String OUTPUT_FILE_PROPERTY = "outputFile";

    public static void main(String[] args) {
        String rootPackage = parseProperty(ROOT_PACKAGE_PROPERTY);
        String outputFile = parseProperty(OUTPUT_FILE_PROPERTY);

        var report = EncapsulationReport.analyze(rootPackage);
        EncapsulationReport.renderPng(report, new File(outputFile));
    }

    private static String parseProperty(String property) {
        String value = System.getProperty(property);
        if (value == null) {
            throw new RuntimeException("Program argument '" + property + "' is not set");
        }

        return value;
    }
}
