package ioc.session;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.lang.reflect.InvocationTargetException;

public class WebDriverFactory {
    private static final ThreadLocal<WebDriver> drv = new ThreadLocal<>();

    public static WebDriver getDriver() {
        WebDriver d = drv.get();
        if (d == null) {
            ChromeOptions opts = new ChromeOptions();
            opts.addArguments("--no-sandbox", "--disable-dev-shm-usage");
            WebDriverManager webDriverManager = WebDriverManager.chromedriver();
            webDriverManager.config()
                    .setCachePath("./target/driver")
                    .setResolutionCachePath("./target/driver/resolution");
            webDriverManager.clearDriverCache().clearResolutionCache();
            webDriverManager.capabilities(opts);
            d = webDriverManager.create();
            drv.set(d);
        }
        return d;
    }

    public static void quitDriver() {
        WebDriver d = drv.get();
        if (d != null) {
            try {
                d.quit();
            } catch (Exception ignored) {

            }
            drv.remove();
        }
    }
}
