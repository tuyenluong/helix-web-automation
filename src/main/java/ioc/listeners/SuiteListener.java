package ioc.listeners;

import lombok.extern.slf4j.Slf4j;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.util.Objects;

@Slf4j
public class SuiteListener implements ISuiteListener {


    @ioc.annotations.Session
    private ioc.api.Session session;

    @Override
    public void onStart(ISuite suite) {
        System.out.println("Is Session init? "+ Objects.nonNull(session));
    }

    @Override
    public void onFinish(ISuite suite) {
        System.out.println("Is Session terminated? "+ Objects.isNull(session));
    }
}
