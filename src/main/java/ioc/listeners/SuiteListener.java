package ioc.listeners;

import ioc.AnnoTestSession;
import ioc.ITestSession;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.util.Objects;

public class SuiteListener implements ISuiteListener {

    @AnnoTestSession
    private ITestSession iTestSession;

    @Override
    public void onStart(ISuite suite) {
        System.out.println("Is Session init? "+ Objects.nonNull(iTestSession));
    }

    @Override
    public void onFinish(ISuite suite) {
        System.out.println("Is Session terminated? "+ Objects.isNull(iTestSession));
    }
}
