package tests.tests;

import ioc.annotations.Driver;
import ioc.annotations.Inject;
import ioc.session.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.pages.LoginPage;

public class UsingMultipleDriverForEachTestMethod {

    @Driver
    private WebDriver driver;

    @Inject
    private LoginPage loginPage;

    @BeforeMethod
    public void beforeTest(){
        WebDriverFactory.getDriver();
        System.out.println(WebDriverFactory.getDriver().hashCode());
    }

    @AfterMethod
    public void afterTest(){
        System.out.println("Closing");
        WebDriverFactory.quitDriver();
    }

    @Test
    public void testTitle1() {
        loginPage.initVnexpress();
        System.out.println("Title: " + loginPage.getPageTitle());
    }

    @Test
    public void testTitle2() {
        loginPage.initViblo();
        System.out.println("Title: " + driver.getTitle() + 2);
    }
}
