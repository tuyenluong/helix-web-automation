import com.google.common.reflect.ClassPath;
import ioc.core.TestNgRunner;
import org.testng.SuiteRunner;
import org.testng.TestNG;
import org.testng.xml.XmlMethodSelector;
import org.testng.xml.XmlSuite;
import tests.tests.UsingMultipleDriverForEachTestMethod;
import tests.tests.UsingOneDriverForOneUsingMultipleDriverForEachTestMethodClass;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestRunner {
    public static void main(String[] args) throws IOException {
        TestNG testng = new TestNG();
        ClassLoader classLoader = TestRunner.class.getClassLoader();
        Class[] classes = ClassPath.from(classLoader).getTopLevelClassesRecursive("tests.tests").stream().map(ClassPath.ClassInfo::load).toArray(Class[]::new);
        testng.setTestClasses(classes);
        testng.setThreadCount(5);
        testng.setParallel(XmlSuite.ParallelMode.METHODS);
        testng.run();
//        new TestNgRunner().run();
    }
}
