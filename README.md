# Encapsulation Report

This library produces a report on the dependencies between packages and jars. The dependency encapsulation report can be used by software architects to assess the level of encapsulation of the dependencies. The report can be generated either as standard output or as a PNG image.

## Why encapsulate dependencies?

Encapsulating dependencies is important for several reasons:

1. **Loose coupling**: Encapsulating dependencies helps to decouple the code that depends on a particular library or module from the implementation of that dependency. This makes it easier to change the implementation without affecting the code that depends on it.

2. **Ease of testing**: When dependencies are encapsulated, it is easier to write unit tests for the code that depends on them. This is because the tests can mock or stub the dependencies, rather than relying on the actual implementation.

3. **Ease of maintenance**: Encapsulating dependencies makes it easier to maintain the codebase because it is clear which parts of the code depend on which libraries or modules. This makes it easier to update or refactor those dependencies without affecting the rest of the code.

4. **Separation of concerns**: Encapsulating dependencies helps to separate different concerns within the codebase. For example, the code that uses a library to perform a specific task can be separated from the code that handles other tasks. This makes it easier to understand and work with the code.

## Usage

### Publish to maven local

The library is not published to any public repository and must be manually published to maven local or a private repository.

Run `./gradlew publishToMavenLocal` to publish locally.

### Maven

Include the local dependency via Maven:

```xml
<!-- When using with provided main classes: -->
<dependency>
  <groupId>io.erkki.encapsulationreport</groupId>
  <artifactId>encapsulation-report</artifactId>
  <version>0.6.0</version>
</dependency>

<!-- When using via unit tests: -->
<dependency>
  <groupId>io.erkki.encapsulationreport</groupId>
  <artifactId>encapsulation-report</artifactId>
  <version>0.6.0</version>
  <scope>test</scope>
</dependency>
```

### Gradle

```groovy
// Make sure maven local is in use:
repositories {
    mavenLocal()
}

dependencies {
    // When using with provided main classes:
    implementation 'io.erkki.encapsulationreport:encapsulation-report:0.6.0'

    // When using via unit tests:
    testImplementation 'io.erkki.encapsulationreport:encapsulation-report:0.6.0'
}
```

### Via provided Main classes

Provided Main classes for stdout and png output can be used, e.g. with following Gradle config:

```groovy
task stdout(type: JavaExec) {
    group = "Execution"
    description = "Prints encapsulation report to stdout"
    classpath = sourceSets.main.runtimeClasspath
    mainClass = "io.erkki.encapsulationreport.run.StdoutMain"
    systemProperties = [
        'rootPackage': 'io.erkki.encapsulationreport',
        // Exclusions delimited by |
        'exclude': '/build/classes/java/test/'
    ]
}

task pngOut(type: JavaExec) {
    group = "Execution"
    description = "Produces encapsulation report as png"
    classpath = sourceSets.main.runtimeClasspath
    mainClass = "io.erkki.encapsulationreport.run.PngOutMain"
    systemProperties = [
        'rootPackage': 'io.erkki.encapsulationreport',
        'outputFile' : 'build/graph.png'
    ]
}
```

Then ran as `./gradlew stdout` or `./gradlew pngOut`

### In unit tests

```java
import io.erkki.encapsulationreport.EncapsulationReport;

// ...

String rootPackage = "io.erkki.encapsulationreport"; // Use your project root package
var report = EncapsulationReport.analyze(rootPackage); 

// Print to console:
EncapsulationReport.print(report);

// Produce PNG graph:
EncapsulationReport.renderPng(report, new File("build/graph2.png"));
```

## Known issues

  * Visual: In dependency graph arrows should start from packages and not points inside a package. It is not straightforward to 
do that with graphviz, although possible. 