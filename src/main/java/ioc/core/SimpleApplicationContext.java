//package ioc.core;
//
//import ioc.annotations.*;
//import ioc.session.WebDriverFactory;
//import org.openqa.selenium.support.FindBy;
//import org.reflections.Reflections;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class SimpleApplicationContext {
//    private final Map<Class<?>, Object> singletons = new ConcurrentHashMap<>();
//    private final String basePackage;
//
//    public SimpleApplicationContext(String basePackage) {
//        this.basePackage = basePackage;
//        init(basePackage);
//    }
//
//    private void init(String basePackage) {
//        Reflections r = new Reflections(basePackage);
//        Set<Class<?>> types = r.getTypesAnnotatedWith(Component.class);
//        for (Class<?> cls : types) {
//            if (isSingleton(cls)) {
//                createBean(cls);
//            }
//        }
//        singletons.values().forEach(this::invokePostConstructs);
//    }
//
//    private boolean isSingleton(Class<?> cls) {
//        Scope s = cls.getAnnotation(Scope.class);
//        return s == null || "singleton".equalsIgnoreCase(s.value());
//    }
//
//    private Object createBean(Class<?> cls) {
//        if (singletons.containsKey(cls)) return singletons.get(cls);
//        try {
//            Constructor<?> ctor = Arrays.stream(cls.getConstructors())
//                    .filter(c -> c.isAnnotationPresent(Inject.class))
//                    .findFirst()
//                    .orElse(null);
//
//            Object instance;
//            if (ctor != null) {
//                Object[] args = Arrays.stream(ctor.getParameterTypes())
//                        .map(this::getBean)
//                        .toArray();
//                instance = ctor.newInstance(args);
//            } else {
//                instance = cls.getDeclaredConstructor().newInstance();
//            }
//
//            if (isSingleton(cls)) singletons.put(cls, instance);
//
//            // field injection
//            for (Field f : cls.getDeclaredFields()) {
//                if (f.isAnnotationPresent(Inject.class)) {
//                    Object dep = getBean(f.getType());
//                    f.setAccessible(true);
//                    f.set(instance, dep);
//                } else if (f.isAnnotationPresent(Driver.class)) {
//                    f.setAccessible(true);
//                    f.set(instance, WebDriverFactory.getDriver());
//                } else if (f.isAnnotationPresent(FindBy.class)) {
//                    f.setAccessible(true);
//                    Object proxy = ElementProxyFactory.createProxyForField(instance, f);
//                    f.set(instance, proxy);
//                }
//            }
//
//            return instance;
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to create bean for " + cls, e);
//        }
//    }
//
//    public <T> T getBean(Class<T> cls) {
//        Object b = singletons.get(cls);
//        if (b != null) return cls.cast(b);
//
//        // find assignable
//        for (Map.Entry<Class<?>, Object> e : singletons.entrySet()) {
//            if (cls.isAssignableFrom(e.getKey())) {
//                return cls.cast(e.getValue());
//            }
//        }
//
//        // attempt create if annotated
//        if (cls.isAnnotationPresent(Component.class)) {
//            Object bean = createBean(cls);
//            if (isSingleton(cls)) singletons.put(cls, bean);
//            return cls.cast(bean);
//        }
//
//        throw new RuntimeException("No bean found for " + cls);
//    }
//
//    public void injectInto(Object instance) {
//        Class<?> cls = instance.getClass();
//        try {
//            for (Field f : cls.getDeclaredFields()) {
//                if (f.isAnnotationPresent(Inject.class)) {
//                    Object dep = getBean(f.getType());
//                    f.setAccessible(true);
//                    f.set(instance, dep);
//                } else if (f.isAnnotationPresent(Driver.class)) {
//                    f.setAccessible(true);
//                    f.set(instance, WebDriverFactory.getDriver());
//                } else if (f.isAnnotationPresent(FindBy.class)) {
//                    f.setAccessible(true);
//                    Object proxy = ElementProxyFactory.createProxyForField(instance, f);
//                    f.set(instance, proxy);
//                }
//            }
//            invokePostConstructs(instance);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException("Failed to inject into " + cls, e);
//        }
//    }
//
//    private void invokePostConstructs(Object bean) {
//        for (Method m : bean.getClass().getDeclaredMethods()) {
//            if (m.isAnnotationPresent(PostConstruct.class)) {
//                try { m.setAccessible(true); m.invoke(bean);} catch (Exception ignored){}
//            }
//        }
//    }
//
//    public void shutdown() {
//        for (Object b : singletons.values()) {
//            for (Method m : b.getClass().getDeclaredMethods()) {
//                if (m.isAnnotationPresent(PreDestroy.class)) {
//                    try { m.setAccessible(true); m.invoke(b);} catch (Exception ignored){}
//                }
//            }
//        }
//        WebDriverFactory.quitDriver();
//    }
//}
