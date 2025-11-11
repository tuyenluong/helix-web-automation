package tests.pages;

import ioc.annotations.Component;
import ioc.annotations.Driver;
import ioc.annotations.Session;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

@Component
public class LoginPage {

    @Session
    private ioc.api.Session session;

    @Driver
    private WebDriver driver;

    @FindBy(xpath = "//header//a[@title='Báo VnExpress - Báo tiếng Việt nhiều người xem nhất']")
    private WebElement vnExpress_title;

    public void initVnexpress() {
        driver.get("https://vnexpress.net/");
    }

    public void initViblo() {
        driver.get("https://viblo.asia/");
    }

    public String getPageTitle(){
        System.out.println("Driver from Session field: "+ session.getWebDriver().hashCode());
        System.out.println("Driver from Driver field: "+ driver.hashCode());
        return vnExpress_title.getAttribute("title");
    }
}
