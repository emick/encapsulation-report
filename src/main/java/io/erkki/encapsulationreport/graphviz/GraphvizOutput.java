package io.erkki.encapsulationreport.graphviz;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;
import io.erkki.encapsulationreport.core.Dependency;
import io.erkki.encapsulationreport.core.DependencyEncapsulationReport;
import io.erkki.encapsulationreport.core.Jar;
import io.erkki.encapsulationreport.core.PackageHierarchyEntry;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;

/**
 * Render {@link DependencyEncapsulationReport} as an image using Graphviz.
 */
public class GraphvizOutput {

    public static void renderPng(DependencyEncapsulationReport report, File outputFile) {
        List<Graph> graphs = report.rootPackages().stream()
                .map(rootPackage -> subgraphFromPackage(rootPackage))
                .toList();

        List<Dependency> dependencies = report.asDependencies();

        List<Node> jars = dependencies.stream()
                .map(pkgJar -> node(pkgJar.jar().filename()).with(Style.FILLED))
                .toList();

        Graph g = graph("root").directed()
                .graphAttr().with(Rank.dir(Rank.RankDir.LEFT_TO_RIGHT))
                .with(graph("jars").cluster().graphAttr().with(Color.TRANSPARENT).with(jars))
                .with(graphs);

        try {
            Graphviz.fromGraph(g)
                    .render(Format.PNG)
                    .toFile(outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Graph subgraphFromPackage(PackageHierarchyEntry pkg) {
        List<Graph> subgraphs = pkg.subpackages().stream()
                .map(subgraph -> subgraphFromPackage(subgraph))
                .toList();

        String graphName = "container-" + pkg.pkg().name();
        Graph result = graph(graphName)
                .directed()
                .cluster()
                .with(subgraphs);

        if (!pkg.dependencies().isEmpty()) {
            result = result.graphAttr().with(Label.of(pkg.pkg().name()));
            result = result.graphAttr().with(Color.BLACK);

            result = result.with(node(pkg.pkg().name())
                    .with(Label.of(""))
                    .with(Shape.POINT));

            for (Jar dep : pkg.dependencies()) {
                result = result.with(node(pkg.pkg().name())
                        .link(node(dep.filename())));
            }

            return result;
        } else {
            // Hide packages without dependencies
            result = result.graphAttr().with(Color.TRANSPARENT);
        }

        return result;
    }
}
