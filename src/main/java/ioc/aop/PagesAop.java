package ioc.aop;

import ioc.annotations.Inject;
import ioc.session.SessionFactory;
import ioc.wrappers.RobustFieldDecorator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Aspect
public class PagesAop {

    static {
        System.out.println(">>> PagesAop class loaded");
    }

    @Pointcut("( (within(tests.pages..*) || within(tests.tests..*)) ) && " +
            "!within(ioc..*) &&" +
            "@annotation(ioc.annotations.Inject)")
    public void pagePointcut() {}

    @Before("pagePointcut()")
    public void pagePointcut(JoinPoint jp){
        Object owner = jp.getTarget();
        initPageObject(owner);
    }

    private void initPageObject(Object object){
        if (object == null) return;
        Class<?> cls = object.getClass();
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(Inject.class)) {
                boolean accessible = f.canAccess(object);
                try {
                    f.setAccessible(true);
                    Object current = f.get(object);
                    if (current == null) {
                        Object instance = null;
                        try {
                            Constructor<?> constructor = f.getType().getConstructor();
                            instance = constructor.newInstance();
                        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            // Handle exceptions
                            e.printStackTrace();
                        }
                        f.set(object, instance);
                        PageFactory.initElements(new RobustFieldDecorator(SessionFactory.getSession().getWebDriver(),3,3),instance);
                        System.out.println("[PagesAop] Injected Pages into " + object.getClass().getName() + "." + f.getName());
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
