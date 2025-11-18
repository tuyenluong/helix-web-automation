package helix.selenium;

import org.openqa.selenium.WebDriver;

public class BrowserDriverManager {

    public static WebDriver chromeDriver(){
        return new ChromeDriverManager().getDriver();
    }
}
