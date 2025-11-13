package tests.tests;

import ioc.Driver;
import ioc.listeners.SuiteListener;
import ioc.listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

@Listeners({SuiteListener.class, TestListener.class})
public class UsingOneDriverForOneUsingMultipleDriverForEachTestMethodClass {

    @Driver
    private WebDriver driver;

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
