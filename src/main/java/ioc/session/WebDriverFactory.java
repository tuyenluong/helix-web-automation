package ioc.session;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
    private static final ThreadLocal<WebDriver> drv = new ThreadLocal<>();

    public static WebDriver getDriver() {
        WebDriver d = drv.get();
        if (d == null) {
            WebDriverManager.chromedriver().clearDriverCache();
            ChromeOptions opts = new ChromeOptions();
            opts.addArguments("--no-sandbox", "--disable-dev-shm-usage");
            d = new ChromeDriver(opts);
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
