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
            try {
                System.out.println("webdriver.chrome.driver = " + System.getProperty("webdriver.chrome.driver"));
                d = webDriverManager.create();
            } catch (Exception e) {
                // Walk cause chain to find InvocationTargetException and its target
                Throwable t = e;
                InvocationTargetException ite = null;
                while (t != null) {
                    if (t instanceof InvocationTargetException) {
                        ite = (InvocationTargetException) t;
                        break;
                    }
                    t = t.getCause();
                }

                if (ite != null) {
                    Throwable ctorThrown = ite.getTargetException(); // the actual exception thrown by the ChromeDriver ctor
                    System.err.println("ChromeDriver ctor threw (target): " + ctorThrown);
                    ctorThrown.printStackTrace();
                    throw new RuntimeException("ChromeDriver ctor failed", ctorThrown);
                } else {
                    // fallback: print entire chain for diagnostics
                    System.err.println("Wrapper exception: " + e);
                    e.printStackTrace();
                    throw new RuntimeException("Failed to create WebDriver", e);
                }
            }
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
