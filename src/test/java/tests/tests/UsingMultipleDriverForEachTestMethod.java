package tests.tests;

import helix.AnnoTestSession;
import helix.ITestSession;
import helix.Inject;
import helix.listeners.SuiteListener;
import helix.listeners.TestListener;
import org.testng.annotations.*;
import tests.pages.LoginPage;

@Listeners({SuiteListener.class, TestListener.class})
public class UsingMultipleDriverForEachTestMethod {

    @AnnoTestSession
    private ITestSession iTestSession;

    @Inject
    private LoginPage loginPage;

    @Test()
    public void testTitle1() {
        loginPage.initVnexpress();
        System.out.println("Title: " + loginPage.getPageTitle());
    }

    @Test
    public void testTitle2() {
        System.out.println("Title: " + loginPage.initViblo().getPageTitle() + 2);
    }
}
