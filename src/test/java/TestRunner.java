import com.google.common.reflect.ClassPath;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.IOException;
import java.util.List;

public class TestRunner {
    public static void main(String[] args) {
        TestNG testng = new TestNG();
        testng.setThreadCount(5);
        testng.setParallel(XmlSuite.ParallelMode.METHODS);
        testng.setOutputDirectory("target");

        XmlSuite xmlSuite = new XmlSuite();
        xmlSuite.setListeners(List.of("ioc.listeners"));

        XmlTest xmlTest = new XmlTest();
        XmlClass xmlClass1 = new XmlClass();
//        XmlClass xmlClass2 = new XmlClass();
        XmlInclude xmlInclude1 = new XmlInclude();
//        XmlInclude xmlInclude2 = new XmlInclude();

        xmlInclude1.setName("testTitle1");
//        xmlInclude2.setName("testTitle1");

        xmlClass1.setName("tests.tests.UsingMultipleDriverForEachTestMethod");
        xmlClass1.setIncludedMethods(List.of(xmlInclude1));

        xmlTest.setClasses(List.of(xmlClass1));

        xmlSuite.setTests(List.of(xmlTest));

        testng.setXmlSuites(List.of(xmlSuite));
        testng.run();

    }
}
