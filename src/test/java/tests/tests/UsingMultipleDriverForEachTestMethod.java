package tests.tests;

import ioc.annotations.Inject;
import ioc.annotations.Session;
import ioc.listeners.SuiteListener;
import org.testng.annotations.*;
import tests.pages.LoginPage;

@Listeners(SuiteListener.class)
public class UsingMultipleDriverForEachTestMethod {

    @Session
    private ioc.api.Session session;

    @Inject
    private LoginPage loginPage;

    @Test
    public void testTitle1() {
        loginPage.initVnexpress();
        System.out.println("Title: " + loginPage.getPageTitle());
    }

    @Test
    public void testTitle2() {
        loginPage.initViblo();
        System.out.println("Title: " + loginPage.getPageTitle() + 2);
    }
}
