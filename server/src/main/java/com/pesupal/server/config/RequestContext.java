package com.pesupal.server.config;

import java.util.HashMap;
import java.util.Map;

public final class RequestContext {

    private static final ThreadLocal<Map<String, Object>> context = ThreadLocal.withInitial(HashMap::new);

    private RequestContext() {
        // Prevent instantiation
    }

    // Set any value
    public static void set(String key, Object value) {
        context.get().put(key, value);
    }

    // Get any value with type safety
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> clazz) {
        Object value = context.get().get(key);
        return clazz.isInstance(value) ? (T) value : null;
    }

    // Remove a key (optional)
    public static void remove(String key) {
        context.get().remove(key);
    }

    // Clear everything after request is complete
    public static void clear() {
        context.remove();
    }

    // For debugging or tracing (optional)
    public static Map<String, Object> getAll() {
        return new HashMap<>(context.get());
    }

    public static Long getLong(String key) {
        Object value = context.get().get(key);
        return value != null ? Long.valueOf(value.toString()) : null;
    }

}
