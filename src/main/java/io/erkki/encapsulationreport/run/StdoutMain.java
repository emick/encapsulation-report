package io.erkki.encapsulationreport.run;

import io.erkki.encapsulationreport.EncapsulationReport;

import java.util.List;
import java.util.Optional;

public class StdoutMain extends CommandLineApp {

    public static void main(String[] args) {
        String rootPackage = parseProperty(ROOT_PACKAGE_PROPERTY);
        Optional<String> exclusions = parseOptionalProperty(EXCLUSIONS_PROPERTY);

        List<String> exclusionList = parseExclusions(exclusions.orElse(null));

        var report = EncapsulationReport.analyze(rootPackage, exclusionList);
        EncapsulationReport.print(report);
    }
}
