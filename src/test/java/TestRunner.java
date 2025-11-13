import com.google.common.reflect.ClassPath;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.io.IOException;
import java.util.List;

public class TestRunner {
    public static void main(String[] args) throws IOException {
        TestNG testng = new TestNG();
        XmlSuite suite = new XmlSuite();
        ClassLoader classLoader = TestRunner.class.getClassLoader();
        var testClasses = ClassPath.from(classLoader).getTopLevelClassesRecursive("tests.tests")
                .stream().map(ClassPath.ClassInfo::load).toArray(Class[]::new);
        var listeners = ClassPath.from(classLoader).getTopLevelClassesRecursive("ios.listeners")
                .stream().map(ClassPath.ClassInfo::load).toArray(Class[]::new);
        testng.setTestClasses(testClasses);
        testng.setThreadCount(5);
        testng.setListenerClasses(List.of(listeners));
        testng.setParallel(XmlSuite.ParallelMode.METHODS);
        testng.run();

    }
}
