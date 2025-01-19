package co.icreated.portal.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;

public class PortalUtils {

    @SuppressWarnings("unchecked")
    public static Object changeAnnotationValue(Annotation annotation, String key, Object newValue) {
        try {
            Object handler = Proxy.getInvocationHandler(annotation);
            Field field = handler.getClass().getDeclaredField("memberValues");
            field.setAccessible(true);

            @SuppressWarnings("unchecked")
            Map<String, Object> memberValues = (Map<String, Object>) field.get(handler);
            Object oldValue = memberValues.get(key);

            if (oldValue == null || !oldValue.getClass().isAssignableFrom(newValue.getClass())) {
                throw new IllegalArgumentException();
            }
            memberValues.put(key, newValue);
            return oldValue;
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            throw new IllegalStateException(e);
        }
    }

}
