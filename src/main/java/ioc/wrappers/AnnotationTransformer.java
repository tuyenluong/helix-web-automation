package ioc.wrappers;

import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

public class AnnotationTransformer {

    private boolean isTestLike(Method method) {
        return findMetaAnnotation(method, Test.class) != null;
    }

    private <A extends Annotation> A findMetaAnnotation(AnnotatedElement element, Class<A> type) {
        A ann = element.getAnnotation(type);
        if (ann != null) return ann;
        for (Annotation meta : element.getAnnotations()) {
            A found = findMetaAnnotation(meta.annotationType(), type);
            if (found != null) return found;
        }
        return null;
    }
}
