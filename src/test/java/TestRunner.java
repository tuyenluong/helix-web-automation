import com.google.common.reflect.ClassPath;
import ioc.core.TestNgRunner;
import org.testng.SuiteRunner;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.io.IOException;

public class TestRunner {
    public static void main(String[] args) throws IOException {
        TestNG testng = new TestNG();
        ClassLoader classLoader = TestRunner.class.getClassLoader();
        var classes = ClassPath.from(classLoader).getTopLevelClassesRecursive("tests.tests").stream().map(ClassPath.ClassInfo::load).toArray(Class[]::new);
        testng.setTestClasses(classes);
        testng.setThreadCount(5);
        testng.setParallel(XmlSuite.ParallelMode.METHODS);
        testng.run();
//        new TestNgRunner().run();
    }
}
