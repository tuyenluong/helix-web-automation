package tests.pages;

import ioc.Sessions;
import ioc.Component;
import ioc.Driver;
import ioc.Session;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

@Component
public class LoginPage {

    @Session
    private Sessions sessions;

    @Driver
    private WebDriver driver;

    @FindBy(xpath = "//header//a[@title='Báo VnExpress - Báo tiếng Việt nhiều người xem nhất']")
    private WebElement vnExpress_title;

    public LoginPage initVnexpress() {
        driver.get("https://vnexpress.net/");
        return this;
    }

    public LoginPage initViblo() {
        driver.get("https://viblo.asia/");
        return this;
    }

    public String getPageTitle(){
        System.out.println("Driver from Session field: "+ sessions.getWebDriver().hashCode());
        System.out.println("Driver from Driver field: "+ driver.hashCode());
        return vnExpress_title.getAttribute("title");
    }
}
