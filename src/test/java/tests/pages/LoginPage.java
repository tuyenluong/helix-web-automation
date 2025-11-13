package tests.pages;

import ioc.AnnoTestSession;
import ioc.ITestSession;
import ioc.Component;
import ioc.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

@Component
public class LoginPage {

    @AnnoTestSession
    private ITestSession iTestSession;

    @Driver
    private WebDriver driver;

    @FindBy(xpath = "//header//a[@title='Báo VnExpress - Báo tiếng Việt nhiều người xem nhất']")
    private WebElement vnExpress_title;

    public LoginPage initVnexpress() {
        System.out.println("Open page vnexpress");
        driver.get("https://vnexpress.net/");
        return this;
    }

    public LoginPage initViblo() {
        System.out.println("Open page viblo");
        driver.get("https://viblo.asia/");
        return this;
    }

    public String getPageTitle(){
        System.out.println("Driver from Session field: "+ iTestSession.getWebDriver().hashCode());
        System.out.println("Driver from Driver field: "+ driver.hashCode());
        return vnExpress_title.getAttribute("title");
    }
}
