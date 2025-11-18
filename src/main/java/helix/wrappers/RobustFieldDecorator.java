package helix.wrappers;

import helix.session.SessionFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.lang.reflect.*;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.util.function.Function;

public class RobustFieldDecorator implements FieldDecorator {
    private final ElementLocatorFactory factory;
    private final long waitSeconds;
    private final int maxRetries;

    public RobustFieldDecorator(WebDriver driver, long waitSeconds, int maxRetries) {
        this.factory = new DefaultElementLocatorFactory(driver);
        this.waitSeconds = waitSeconds;
        this.maxRetries = maxRetries;
    }


    @Override
    public Object decorate(ClassLoader loader, Field field) {
        if (field.isSynthetic()) return null;
        String name = field.getName();
        if (name.startsWith("ajc$") || name.contains("$ajc$")) return null;
        if (!field.isAnnotationPresent(FindBy.class)) return null;

        ElementLocator locator = factory.createLocator(field);
        if (locator == null) return null;
        InvocationHandler handler = new RobustElementHandler(locator, waitSeconds, maxRetries);
        return Proxy.newProxyInstance(loader, new Class[]{WebElement.class, WrapsElement.class, Locatable.class},
                handler);
    }

    private static class RobustElementHandler implements InvocationHandler {
        private final ElementLocator locator;
        private final long waitSeconds;
        private final int maxRetries;

        // simple cached element (invalidated on StaleElementReferenceException)
        private volatile WebElement cachedElement = null;


        RobustElementHandler(ElementLocator locator, long waitSeconds, int maxRetries) {
            this.locator = locator;
            this.waitSeconds = waitSeconds;
            this.maxRetries = maxRetries;
        }

        /**
         * Try to extract By via reflection; if that fails, throw and caller will fallback.
         */
        private By extractByFromLocator(ElementLocator locator) {
            Class<?> c = locator.getClass();
            while (c != null && c != Object.class) {
                try {
                    Field f = c.getDeclaredField("by"); // common name
                    f.setAccessible(true);
                    Object val = f.get(locator);
                    if (val instanceof By) return (By) val;
                } catch (NoSuchFieldException ignored) {
                    c = c.getSuperclass();
                } catch (IllegalAccessException iae) {
                    throw new RuntimeException("Cannot access 'by' on locator", iae);
                } catch (Throwable t) {
                    // Any other reflection issue - break and fallback later
                    break;
                }
            }

            // fallback: search declared fields for a By typed field
            for (Field f : locator.getClass().getDeclaredFields()) {
                if (By.class.isAssignableFrom(f.getType())) {
                    try {
                        f.setAccessible(true);
                        Object val = f.get(locator);
                        if (val instanceof By) return (By) val;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Cannot access By field", e);
                    } catch (Throwable t) {
                        // ignore and continue
                    }
                }
            }

            throw new IllegalStateException("Unable to extract By from ElementLocator: " + locator.getClass());
        }

        private <T> T waitFor(WebDriver driver, By by, Duration timeout, Duration pollingTime, Function<WebDriver, T> condition) {
            FluentWait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(timeout)
                    .pollingEvery(pollingTime)
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(ElementNotInteractableException.class);
            return wait.until(condition);
        }

        /**
         * Resolve the real WebElement. Thread-safe (synchronized) to avoid duplicate concurrent finds.
         * Uses:
         *  - preferred: wait by By (if extractable)
         *  - fallback: locator.findElement() + visibilityOf(element)
         * Caches the found element and invalidates on StaleElementReferenceException.
         */
        private WebElement resolve() throws Exception {
            // fast-path: if cached and still displayed, return it
            WebElement local = cachedElement;
            if (local != null) {
                try {
                    if (local.isDisplayed()) return local;
                } catch (StaleElementReferenceException ser) {
                    cachedElement = null; // invalidate
                } catch (WebDriverException wde) {
                    // any driver exception -> invalidate and continue to re-resolve
                    cachedElement = null;
                }
            }

            synchronized (locator) { // synchronize on locator instance for thread-safety
                // re-check after entering synchronized block
                local = cachedElement;
                if (local != null) {
                    try {
                        if (local.isDisplayed()) return local;
                    } catch (StaleElementReferenceException ser) {
                        cachedElement = null;
                    } catch (WebDriverException wde) {
                        cachedElement = null;
                    }
                }

                Exception last = null;
                WebDriver driver = SessionFactory.getSession().getWebDriver();

                for (int attempt = 1; attempt <= Math.max(1, maxRetries); attempt++) {
                    try {
                        // Preferred: use By if available (more robust for visibilityOfElementLocated)
                        try {
                            By by = extractByFromLocator(locator);
                            WebElement e = waitFor(driver, by, Duration.ofSeconds(waitSeconds), Duration.ofSeconds(30), ExpectedConditions.visibilityOfElementLocated(by));
                            cachedElement = e;
                            return e;
                        } catch (RuntimeException reflectEx) {
                            // reflection couldn't produce a By or had an access issue — fallback below
                        }

                        // Fallback: ask locator to find element, then wait for visibility of that element
                        By by = extractByFromLocator(locator);
                        WebElement e = waitFor(driver, by, Duration.ofSeconds(waitSeconds), Duration.ofSeconds(30), ExpectedConditions.visibilityOfElementLocated(by));
                        cachedElement = e;
                        return e;

                    } catch (StaleElementReferenceException ser) {
                        last = ser;
                        cachedElement = null; // invalidate and retry
                        // small backoff
                        try { Thread.sleep(100L * attempt); } catch (InterruptedException ignored) {}
                        continue;
                    } catch (TimeoutException te) {
                        last = te;
                        cachedElement = null;
                        try { Thread.sleep(120L * attempt); } catch (InterruptedException ignored) {}
                        continue;
                    } catch (NoSuchElementException | ElementNotInteractableException e) {
                        // These are likely non-transient — record and break (or continue depending on your policy)
                        last = e;
                        // give it a short pause and retry a couple times
                        try { Thread.sleep(150L * attempt); } catch (InterruptedException ignored) {}
                        continue;
                    } catch (WebDriverException wde) {
                        last = wde;
                        cachedElement = null;
                        try { Thread.sleep(120L * attempt); } catch (InterruptedException ignored) {}
                        continue;
                    } catch (Exception ex) {
                        last = ex;
                        cachedElement = null;
                        try { Thread.sleep(120L * attempt); } catch (InterruptedException ignored) {}
                        continue;
                    }
                }

                if (last != null) {
                    if (last instanceof RuntimeException) throw (RuntimeException) last;
                    throw last;
                }
                throw new NoSuchElementException("Unable to locate element with locator: " + locator);
            } // end synchronized
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // Short-circuit benign Object methods to avoid expensive resolution:
            String mName = method.getName();
            if ("toString".equals(mName) && method.getParameterCount() == 0) {
                return "RobustWebElementProxy[" + locator + "]";
            }
            if ("hashCode".equals(mName) && method.getParameterCount() == 0) {
                return System.identityHashCode(proxy);
            }
            if ("equals".equals(mName) && method.getParameterCount() == 1) {
                return proxy == args[0];
            }

            // Special: getWrappedElement should return underlying element (resolve but cheap if cached)
            if ("getWrappedElement".equals(mName) && method.getParameterCount() == 0) {
                return resolve();
            }

            // Normal case: resolve element (wait/retry) and invoke method on it
            WebElement element = null;
            try {
                element = resolve();
            } catch (Throwable t) {
                // propagate meaningful exception for callers
                throw t;
            }

            try {
                return method.invoke(element, args);
            } catch (InvocationTargetException ite) {
                Throwable cause = ite.getCause();
                // If it's a stale element, invalidate cached element and retry once
                if (cause instanceof StaleElementReferenceException) {
                    cachedElement = null;
                    WebElement reResolved = resolve();
                    try {
                        return method.invoke(reResolved, args);
                    } catch (InvocationTargetException ite2) {
                        throw ite2.getCause();
                    }
                }
                throw cause;
            }
        }
    }
}
