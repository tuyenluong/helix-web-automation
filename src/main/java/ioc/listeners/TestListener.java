package ioc.listeners;

import ioc.AnnoTestSession;
import ioc.ITestSession;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Objects;

public class TestListener implements ITestListener {

    @AnnoTestSession
    private ITestSession iTestSession;

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Is Test Session init? "+ Objects.nonNull(iTestSession));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
    }

    @Override
    public void onTestFailure(ITestResult result) {
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        this.onTestFailure(result);
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }
}
