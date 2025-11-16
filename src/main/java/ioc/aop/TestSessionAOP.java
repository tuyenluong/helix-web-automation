package ioc.aop;

import ioc.AnnoTestSession;
import ioc.constant.AopConstant;
import ioc.session.SessionFactory;
import ioc.session.SessionsImp;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Field;
import java.util.Objects;

@Aspect
public class TestSessionAOP {
    static {
        System.out.println(">>> SessionAOP class loaded");
    }

    @Before(AopConstant.SESSIONS_ON_START_TEST_LISTENER_POINT_CUT)
    public void onStartSessionAdvice(JoinPoint jp) {
        Object owner = jp.getTarget();
        injectSession(owner);
    }
    @Before(AopConstant.SESSIONS_FIELD_POINT_CUT)
    public void onFieldGetSessionAdvice(JoinPoint jp) {
        Object owner = jp.getTarget();
        injectSession(owner);
    }

    @After(AopConstant.SESSIONS_ON_FINISH_TEST_LISTENER_POINT_CUT)
    public void removeSessionMethodAdvice(JoinPoint jp) {
        Object owner = jp.getTarget();
        removeSession(owner);
    }

    private void injectSession(Object obj) {
        if (obj == null) return;
        Class<?> cls = obj.getClass();
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(AnnoTestSession.class)) {
                boolean accessible = f.canAccess(obj);
                try {
                    f.setAccessible(true);
                    Object current = f.get(obj);
                    if (Objects.isNull(current)) {
                        if(Objects.isNull(SessionFactory.getSession())){
                            SessionFactory.setSession(new SessionsImp());
                        }
                        Object session = SessionFactory.getSession();
                        f.set(obj, session);
                        System.out.println("[TestSessionAOP] Injected Session into " + cls.getName() + "." + f.getName());
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
            if (f.isAnnotationPresent(AnnoTestSession.class)) {
                boolean accessible = f.canAccess(obj);
                try {
                    f.setAccessible(true);
                    Object current = f.get(obj);
                    if (Objects.nonNull(current)) {
                        SessionFactory.removeSession();
                        f.set(obj, null);
                        System.out.println("[TestSessionAOP] Remove Session at " + cls.getName() + "." + f.getName());
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
