package helix.aop;

import helix.Driver;
import helix.constant.AopConstant;
import helix.selenium.BrowserDriverManager;
import helix.session.SessionFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.lang.reflect.Field;
import java.util.Objects;

@Aspect
public class DriverAop {

    static {
        System.out.println(">>> DriverAop class loaded");
    }

    @Before(AopConstant.WEBDRIVER_POINT_CUT)
    public void getDriverMethodAdvice(JoinPoint jp) {
        // careful: jp.getThis() is the object doing the read; jp.getTarget() or the signature can help find the field owner
        Object owner = jp.getTarget(); // often the owning object when access is 'this.driver'
        injectDriverFields(owner);
    }

    private void injectDriverFields(Object obj) {
        if (obj == null) return;
        Class<?> cls = obj.getClass();
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(Driver.class)) {
                boolean accessible = f.canAccess(obj);
                try {
                    f.setAccessible(true);
                    Object current = f.get(obj);
                    if (Objects.isNull(current)) {
                        if(Objects.isNull(SessionFactory.getSession().getWebDriver())){
                            SessionFactory.getSession().setWebDriver(BrowserDriverManager.chromeDriver());
                        }
                        Object driver = SessionFactory.getSession().getWebDriver();
                        f.set(obj, driver);
                        System.out.println("[DriverAop] Injected WebDriver into " + cls.getName() + "." + f.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    f.setAccessible(accessible);
                }
            }
        }
    }
}
