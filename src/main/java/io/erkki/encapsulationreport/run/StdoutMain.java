package io.erkki.encapsulationreport.run;

import io.erkki.encapsulationreport.EncapsulationReport;

public class StdoutMain {

    public static final String ROOT_PACKAGE_PROPERTY = "rootPackage";

    public static void main(String[] args) {
        String rootPackage = System.getProperty("rootPackage");
        if (rootPackage == null) {
            throw new RuntimeException("Program argument '" + ROOT_PACKAGE_PROPERTY + "' is not set");
        }

        var report = EncapsulationReport.analyze(rootPackage);
        EncapsulationReport.print(report);
    }
}
