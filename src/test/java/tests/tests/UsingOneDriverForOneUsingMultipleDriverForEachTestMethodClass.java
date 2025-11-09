package tests.tests;

import ioc.annotations.Driver;
import ioc.session.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class UsingOneDriverForOneUsingMultipleDriverForEachTestMethodClass {

    @Driver
    private WebDriver driver;

    @BeforeClass
    public void beforeClass(){
        WebDriverFactory.getDriver();
        System.out.println(WebDriverFactory.getDriver().hashCode());
    }

    @AfterClass
    public void afterClass() {
        System.out.println("Closing");
        WebDriverFactory.quitDriver();
    }

    @Test
    public void testTitle1() {
        driver.get("https://google.com");
        System.out.println("Title: " + driver.getTitle() + 1);
    }

    @Test
    public void testTitle2() {
        System.out.println("Title: " + driver.getTitle() + 2);
    }
}
