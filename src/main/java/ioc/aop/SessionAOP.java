package ioc.aop;

import ioc.annotations.Session;
import ioc.session.SessionFactory;
import ioc.session.SessionImp;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Field;
import java.util.Objects;

@Aspect
public class SessionAOP {
    static {
        System.out.println(">>> SessionAOP class loaded");
    }

    @Pointcut("execution(void ioc.listeners.SuiteListener.onStart(..))")
    public void onStartSessionSuitePointCut() {}

    @Pointcut("execution(void ioc.listeners.SuiteListener.onFinish(..))")
    public void onFinishSessionSuitePointCut() {}

    @Pointcut("get(private ioc.api.Session ioc.*.*.*) || get(private ioc.api.Session tests.*.*.*)")
    public void getSessionPointCut() {}

    @Before("getSessionPointCut() || onStartSessionSuitePointCut()")
    public void getSessionMethodAdvice(JoinPoint jp) {
        Object owner = jp.getTarget();
        injectSession(owner);
    }

    @After("onFinishSessionSuitePointCut()")
    public void removeSessionMethodAdvice(JoinPoint jp) {
        Object owner = jp.getTarget();
        removeSession(owner);
    }

    private void injectSession(Object obj) {
        if (obj == null) return;
        Class<?> cls = obj.getClass();
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(Session.class)) {
                boolean accessible = f.canAccess(obj);
                try {
                    f.setAccessible(true);
                    Object current = f.get(obj);
                    if (Objects.isNull(current)) {
                        if(Objects.isNull(SessionFactory.getSession())){
                            SessionFactory.setSession(new SessionImp());
                        }
                        Object session = SessionFactory.getSession();
                        f.set(obj, session);
                        System.out.println("[SessionAOP] Injected Session into " + cls.getName() + "." + f.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    f.setAccessible(accessible);
                }
            }
        }
    }
    private void removeSession(Object obj) {
        if (obj == null) return;
        Class<?> cls = obj.getClass();
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(Session.class)) {
                boolean accessible = f.canAccess(obj);
                try {
                    f.setAccessible(true);
                    Object current = f.get(obj);
                    if (Objects.nonNull(current)) {
                        SessionFactory.removeSession();
                        f.set(obj, null);
                        System.out.println("[SessionAOP] Remove Session at " + cls.getName() + "." + f.getName());
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
