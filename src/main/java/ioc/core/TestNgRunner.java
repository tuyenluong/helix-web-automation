package ioc.core;

import org.testng.TestNG;
import org.testng.annotations.Parameters;
import org.testng.xml.XmlSuite;

import java.util.List;

public class TestNgRunner extends TestNG {

    public TestNgRunner() {}

    @Parameters()
    public TestNgRunner(Class<?>[] classes, List<String> testNames, int threadCount, XmlSuite.ParallelMode  parallelMode) {
    }
}
