package ioc.aop;

import ioc.Session;
import ioc.Sessions;
import ioc.constant.AopConstant;
import ioc.session.SessionFactory;
import ioc.wrappers.FindByBuilt;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;

@Aspect
public class ElementsAop {

    @Session
    private Sessions sessions;

    static {
        System.out.println(">>> ElementsAop class loaded");
    }

//    @Pointcut(AopConstant.FIND_BY_FIELD_POINT_CUT)
//    public void findByPointcut() {}
//
//    @Pointcut("pointcutInPackage() && @annotation(org.openqa.selenium.support.FindBys)")
//    public void findBysPointcut() {}

    @Before(AopConstant.FIND_BY_FIELD_POINT_CUT)
    public void findByAdvice(JoinPoint jp) {
        Object owner = jp.getTarget();
        initWebElementObject(owner);
    }

    private void initWebElementObject(Object object){
        if (object == null) return;
        Class<?> cls = object.getClass();
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(FindBy.class)) {
                boolean accessible = f.canAccess(object);
                try {
                    f.setAccessible(true);
                    Object current = f.get(object);
                    if (current == null) {
                        By byElement = new FindByBuilt().buildIt(f.getAnnotation(FindBy.class),f);
                        WebElement element = sessions.getWebDriver().findElement(byElement);
                        f.set(object, element);
                        System.out.println("[ElementsAop] Injected Elements into " + object.getClass().getName() + "." + f.getName());
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