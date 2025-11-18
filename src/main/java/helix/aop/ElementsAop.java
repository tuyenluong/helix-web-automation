package helix.aop;

import helix.constant.AopConstant;
import helix.session.SessionFactory;
import helix.wrappers.FindByBuilt;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Field;
import java.util.Objects;

@Aspect
public class ElementsAop {

    static {
        System.out.println(">>> ElementsAop class loaded");
    }

    @Before(AopConstant.FIND_BY_FIELD_POINT_CUT)
    public void findByAdvice(JoinPoint jp) {
        Object owner = jp.getTarget();
        initWebElementObject(owner);
    }

    private void initWebElementObject(Object object){
        if (object == null) return;
        Class<?> cls = object.getClass();
        for (Field f : cls.getDeclaredFields()) {
            initFindByElement(f, object);
            initFindBysElement(f, object);
            initFindAllElement(f, object);
        }
    }
    private void initFindByElement(Field f , Object object){
        if (f.isAnnotationPresent(FindBy.class)) {
            boolean accessible = f.canAccess(object);
            try {
                f.setAccessible(true);
                Object current = f.get(object);
                if (Objects.isNull(current)) {
                    By byElement = new FindByBuilt().buildIt(f.getAnnotation(FindBy.class),f);
                    WebElement element = SessionFactory.getSession().getWebDriver().findElement(byElement);
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
    private void initFindBysElement(Field f , Object object){
        if (f.isAnnotationPresent(FindBys.class)) {
            boolean accessible = f.canAccess(object);
            try {
                f.setAccessible(true);
                Object current = f.get(object);
                if (Objects.isNull(current)) {
                    By byElement = new FindByBuilt().buildIt(f.getAnnotation(FindBys.class),f);
                    WebElement element = SessionFactory.getSession().getWebDriver().findElement(byElement);
                    f.set(object, element);
                    PageFactory.initElements(SessionFactory.getSession().getWebDriver(), object);
                    System.out.println("[ElementsAop] Injected Elements into " + object.getClass().getName() + "." + f.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                f.setAccessible(accessible);
            }
        }
    }
    private void initFindAllElement(Field f , Object object){
        if (f.isAnnotationPresent(FindAll.class)) {
            boolean accessible = f.canAccess(object);
            try {
                f.setAccessible(true);
                Object current = f.get(object);
                if (Objects.isNull(current)) {
                    By byElement = new FindByBuilt().buildIt(f.getAnnotation(FindAll.class),f);
                    WebElement element = SessionFactory.getSession().getWebDriver().findElement(byElement);
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