package ioc.core;

import ioc.session.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ElementProxyFactory {

    public static Object createProxyForField(Object parentInstance, Field field) {
        FindBy fr = field.getAnnotation(FindBy.class);
        By by = buildBy(fr);
        Class<?> type = field.getType();

        InvocationHandler handler = (proxy, method, args) -> {
            WebDriver driver = WebDriverFactory.getDriver();
            WebElement elm = driver.findElement(by);
            return method.invoke(elm, args);
        };

        if (WebElement.class.isAssignableFrom(type)) {
            return Proxy.newProxyInstance(
                    type.getClassLoader(),
                    new Class[]{WebElement.class},
                    handler
            );
        }

        throw new UnsupportedOperationException("Unsupported field type: " + type);
    }

    private static By buildBy(FindBy f) {
        if (!f.css().isEmpty()) return By.cssSelector(f.css());
        if (!f.xpath().isEmpty()) return By.xpath(f.xpath());
        throw new IllegalArgumentException("FindBy must have selector");
    }
}
