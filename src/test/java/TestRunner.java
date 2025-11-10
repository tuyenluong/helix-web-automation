import com.google.common.reflect.ClassPath;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.io.IOException;

public class TestRunner {
    public static void main(String[] args) throws IOException {
//        TestNG testng = new TestNG();
//        ClassLoader classLoader = TestRunner.class.getClassLoader();
//        var classes = ClassPath.from(classLoader).getTopLevelClassesRecursive("tests.tests").stream().map(ClassPath.ClassInfo::load).toArray(Class[]::new);
//        testng.setTestClasses(classes);
//        testng.setThreadCount(5);
//        testng.setParallel(XmlSuite.ParallelMode.METHODS);
//        testng.run();
//        new TestNgRunner().run();
        WebDriverManager webDriverManager = WebDriverManager.chromedriver();
        webDriverManager.config().setCachePath("./src/test/resources/driver");
//        webDriverManager.config().setRemoteAddress("localhost:4444");
        webDriverManager.clearDriverCache();
        webDriverManager.setup();

    }
}
