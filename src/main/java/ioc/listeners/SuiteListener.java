package ioc.listeners;

import ioc.Session;
import ioc.Sessions;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.util.Objects;

public class SuiteListener implements ISuiteListener {

    @Session
    private Sessions sessions;

    @Override
    public void onStart(ISuite suite) {
        System.out.println("Is Session init? "+ Objects.nonNull(sessions));
    }

    @Override
    public void onFinish(ISuite suite) {
        System.out.println("Is Session terminated? "+ Objects.isNull(sessions));
    }
}
