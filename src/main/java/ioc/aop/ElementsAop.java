//package ioc.aop;
//
//import ioc.session.WebDriverFactory;
//import ioc.wrappers.FindByBuilt;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//import java.lang.reflect.Field;
//
//@Aspect
//public class ElementsAop {
//    static {
//        System.out.println(">>> ElementsAop class loaded");
//    }
//
//    @Pointcut("get(private org.openqa.selenium.WebElement tests.pages.*.*)")
//    public void pointcutInPackage() {}
//
//    @Pointcut("pointcutInPackage() && @annotation(org.openqa.selenium.support.FindBy)")
//    public void findByPointcut() {}
//
//    @Pointcut("pointcutInPackage() && @annotation(org.openqa.selenium.support.FindBy)")
//    public void findBysPointcut() {}
//
//    @Before("findByPointcut()")
//    public void findByAdvice(JoinPoint jp) {
//        Object owner = jp.getTarget();
//        initWebElementObject(owner);
//    }
//
//    private void initWebElementObject(Object object){
//        if (object == null) return;
//        Class<?> cls = object.getClass();
//        for (Field f : cls.getDeclaredFields()) {
//            if (f.isAnnotationPresent(FindBy.class)) {
//                boolean accessible = f.canAccess(object);
//                try {
//                    f.setAccessible(true);
//                    Object current = f.get(object);
//                    if (current == null) {
//                        By byElement = new FindByBuilt().buildIt(f.getAnnotation(FindBy.class),f);
//                        WebElement element = WebDriverFactory.getDriver().findElement(byElement);
//                        f.set(object, element);
//                        System.out.println("[ElementsAop] Injected Elements into " + object.getClass().getName() + "." + f.getName());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    f.setAccessible(accessible);
//                }
//            }
//        }
//    }
//}
