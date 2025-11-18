package helix.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class ChromeDriverManager {

    private WebDriver driver;
    private WebDriverManager webDriverManager;
    private ChromeOptions opts;

    public ChromeDriverManager(){
        opts = new ChromeOptions();
        opts.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        webDriverManager = WebDriverManager.chromedriver();
        webDriverManager.config()
                .setCachePath("./target/driver")
                .setResolutionCachePath("./target/driver/resolution");
        webDriverManager.clearDriverCache().clearResolutionCache();
        webDriverManager.capabilities(opts);
        driver = webDriverManager.create();
    }

    public WebDriver getDriver(){
        return driver;
    }

    public ChromeDriverManager setOptionArguments(String args){
        opts.addArguments(args);
        return this;
    }

    public ChromeDriverManager setCachePath(String cachePath){
        webDriverManager.config().setCachePath(cachePath);
        return this;
    }

    public ChromeDriverManager setResolutionCachePath(String resolutionCachePath){
        webDriverManager.config().setResolutionCachePath(resolutionCachePath);
        return this;
    }

    public ChromeDriverManager clearDriverCache(){
        webDriverManager.clearDriverCache();
        return this;
    }

    public ChromeDriverManager clearResolutionCache(){
        webDriverManager.clearResolutionCache();
        return this;
    }

}
