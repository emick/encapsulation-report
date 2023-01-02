package io.erkki.encapsulationreport.test2;

import io.erkki.encapsulationreport.test1.Test1;
import org.junit.platform.commons.JUnitException;

/**
 * Dummy test class for jar analysis.
 */
public class Test2 {

    private Test2() {
        new JUnitException("");
        new Test1().graph.enableAllInfo();
    }
}
