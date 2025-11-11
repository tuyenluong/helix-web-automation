//package ioc.hooks;
//
//import ioc.core.SimpleApplicationContext;
//import org.testng.*;
//
//public class TestNgContextListener implements ISuiteListener, IInvokedMethodListener, ITestListener {
//
//    public static final String CTX_KEY = "ioc.ctx";
//    private final boolean perClass = false;
//    private final String basePackage = "com.example.tests";
//
//    // ISuiteListener
//    @Override
//    public void onStart(ISuite suite) {
//        SimpleApplicationContext ctx = new SimpleApplicationContext(basePackage);
//        suite.setAttribute(CTX_KEY, ctx);
//        System.out.println("[IOC] Suite-scoped ApplicationContext created");
//    }
//
//    @Override
//    public void onFinish(ISuite suite) {
//        Object o = suite.getAttribute(CTX_KEY);
//        if (o instanceof SimpleApplicationContext) {
//            ((SimpleApplicationContext) o).shutdown();
//            System.out.println("[IOC] Suite-scoped ApplicationContext shutdown");
//        }
//    }
//
//    // IInvokedMethodListener
//    @Override
//    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
//        // called before each test method (and config methods). Inject dependencies here if needed.
//        Object testInstance = testResult.getInstance();
//        if (testInstance == null) return;
//        ISuite suite = testResult.getTestContext().getSuite();
//        Object o = suite.getAttribute(CTX_KEY);
//        if (o instanceof SimpleApplicationContext) {
//            ((SimpleApplicationContext) o).injectInto(testInstance);
//        }
//    }
//
//    @Override
//    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
//        // called after each test method
//    }
//
//    // ITestListener
//    @Override
//    public void onTestStart(ITestResult result) {}
//
//    @Override
//    public void onTestSuccess(ITestResult result) {}
//
//    @Override
//    public void onTestFailure(ITestResult result) {}
//
//    @Override
//    public void onTestSkipped(ITestResult result) {}
//
//    @Override
//    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
//
//    @Override
//    public void onStart(ITestContext context) {
//        // called before any test methods in this <test> run
//    }
//
//    @Override
//    public void onFinish(ITestContext context) {
//        // called after all test methods in this <test> finish
//    }
//}
