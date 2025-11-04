package ioc.aop;

import ioc.annotations.Driver;
import ioc.session.WebDriverFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.lang.reflect.Field;

@Aspect
public class DriverAop {
    static {
        System.out.println(">>> DriverAop class loaded");
    }

    @Pointcut("@annotation(org.testng.annotations.Test) || withincode(* tests.pages.*.*(..))")
    public void driverPointCut() {}


    @Before("driverPointCut()")
    public void getDriverMethodAdvice(JoinPoint jp) {
        // careful: jp.getThis() is the object doing the read; jp.getTarget() or the signature can help find the field owner
        Object owner = jp.getTarget(); // often the owning object when access is 'this.driver'
        injectDriverFields(owner);     // or locate the declaring class and set field on instance if needed
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
                    if (current == null) {
                        Object driver = WebDriverFactory.getDriver();
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
