package io.erkki.encapsulationreport;

import io.erkki.encapsulationreport.classgraph.ClassGraphDependencyScanner;
import io.erkki.encapsulationreport.core.Dependency;
import io.erkki.encapsulationreport.core.DependencyEncapsulationReport;
import io.erkki.encapsulationreport.core.DependencyEncapsulationReporter;
import io.erkki.encapsulationreport.graphviz.GraphvizOutput;

import java.io.File;
import java.util.List;

/**
 * Entrypoint with default config for encapsulation report.
 */
public class EncapsulationReport {

    public static DependencyEncapsulationReport analyze(String rootPackage) {
        List<Dependency> dependencies = ClassGraphDependencyScanner.scan(rootPackage);
        return new DependencyEncapsulationReporter().report(dependencies);
    }

    public static void print(DependencyEncapsulationReport report) {
        List<Dependency> dependencies = report.asDependencies();
        dependencies.forEach(x -> System.out.println(x.pkg().name() + " -> " + x.jar().filename()));
    }

    public static void renderPng(DependencyEncapsulationReport report, File outputFile) {
        GraphvizOutput.renderPng(report, outputFile);
    }
}
