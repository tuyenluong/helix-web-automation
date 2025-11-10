package tests.pages;

import ioc.annotations.Component;
import ioc.annotations.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

@Component
public class LoginPage {

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
        return vnExpress_title.getAttribute("title");
    }
}
