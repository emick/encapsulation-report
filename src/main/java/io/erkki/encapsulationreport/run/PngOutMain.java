package io.erkki.encapsulationreport.run;

import io.erkki.encapsulationreport.EncapsulationReport;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class PngOutMain extends CommandLineApp {

    public static final String OUTPUT_FILE_PROPERTY = "outputFile";

    public static void main(String[] args) {
        String rootPackage = parseProperty(ROOT_PACKAGE_PROPERTY);
        Optional<String> exclusions = parseOptionalProperty(EXCLUSIONS_PROPERTY);
        String outputFile = parseProperty(OUTPUT_FILE_PROPERTY);

        List<String> exclusionList = parseExclusions(exclusions.orElse(null));

        var report = EncapsulationReport.analyze(rootPackage, exclusionList);
        EncapsulationReport.renderPng(report, new File(outputFile));
    }
}
