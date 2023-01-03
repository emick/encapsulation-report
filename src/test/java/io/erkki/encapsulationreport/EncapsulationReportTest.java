package io.erkki.encapsulationreport;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;

class EncapsulationReportTest {

    public static final String ROOT_PACKAGE = "io.erkki.encapsulationreport";

    @Test
    void shouldPrintDependencyEncapsulationReport() {
        var report = EncapsulationReport.analyze(ROOT_PACKAGE);

        EncapsulationReport.print(report);
    }

    @Test
    void shouldRenderDependencyEncapsulationReportAsPng() {
        var report = EncapsulationReport.analyze(ROOT_PACKAGE);

        EncapsulationReport.renderPng(report, new File("build/graph.png"));
    }

    @Test
    void shouldRenderDependencyEncapsulationReportWithExclusionsAsPng() {
        var report = EncapsulationReport.analyze(ROOT_PACKAGE, Collections.singletonList("/build/classes/java/test/"));

        EncapsulationReport.renderPng(report, new File("build/graph_with_exclusions.png"));
    }
}
