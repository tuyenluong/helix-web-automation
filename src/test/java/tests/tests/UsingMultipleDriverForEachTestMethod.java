package tests.tests;

import ioc.Sessions;
import ioc.Inject;
import ioc.Session;
import ioc.listeners.SuiteListener;
import org.testng.annotations.*;
import tests.pages.LoginPage;

@Listeners(SuiteListener.class)
public class UsingMultipleDriverForEachTestMethod {

    @Session
    private Sessions sessions;

    @Inject
    private LoginPage loginPage;

    @Test
    public void testTitle1() {
        System.out.println("Title: " + loginPage.initVnexpress().getPageTitle());
    }

    @Test
    public void testTitle2() {
        System.out.println("Title: " + loginPage.initViblo().getPageTitle() + 2);
    }
}
