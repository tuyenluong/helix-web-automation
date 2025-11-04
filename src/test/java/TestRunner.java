import org.testng.TestNG;
import org.testng.xml.XmlSuite;

public class TestRunner {
    public static void main(String[] args) {
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] {
                tests.tests.UsingMultipleDriverForEachTestMethod.class,
                tests.tests.UsingOneDriverForOneUsingMultipleDriverForEachTestMethodClass.class
        });
        testng.setThreadCount(5);
        testng.setParallel(XmlSuite.ParallelMode.METHODS);
        testng.run();
    }
}
