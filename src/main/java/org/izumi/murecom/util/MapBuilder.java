package org.izumi.murecom.util;

import org.izumi.murecom.exception.MurecomException;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public final class MapBuilder<K, V> {
    private final Map<K, V> map;

    public MapBuilder() {
        this.map = new HashMap<>();
    }

    public MapBuilder(Map<K, V> map) {
        this.map = new HashMap<>(map);
    }

    public MapBuilder<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    public MapBuilder<K, V> remove(K key) {
        map.remove(key);
        return this;
    }

    public Map<K, V> build() {
        return new HashMap<>(map);
    }

    public <T extends Map<K, V>> T build(Class<T> clazz) {
        try {
            Constructor<?>[] constructors = clazz.getConstructors();
            for (Constructor<?> constructor : constructors) {
                Class<?>[] types = constructor.getParameterTypes();
                if (types.length != 1) {
                    continue;
                }
                Class<?> type = types[0];
                if (Map.class.isAssignableFrom(type)) {
                    return clazz.cast(constructor.newInstance(map));
                }
            }
            throw new MurecomException(); //TODO:
        } catch (Exception ex) {
            throw new MurecomException(); //TODO:
        }
    }
}
