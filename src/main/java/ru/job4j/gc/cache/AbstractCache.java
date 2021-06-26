package ru.job4j.gc.cache;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {

    private final Map<K, SoftReference<V>> cache = new HashMap<>();

    public void put(K key, V value) {
        cache.put(key, new SoftReference<>(value));
    }

    public V get(K key) throws IOException {
        if (cache.containsKey(key)) {
            System.out.println(key + " ключ файла есть в мапе");
            V value = cache.get(key).get();
            if (value != null) {
                System.out.println(key + " файл есть в кэше");
                return value;
            } else {
                System.out.println(key + " файл ОТСУТСТВУЕТ в кэше");
            }
        } else {
            System.out.println(key + " ключ файла ОТСУТСТВУЕТ в мапе");
        }
        System.out.println(key + " файл загружается");
        return load(key);
    }

    protected abstract V load(K key) throws IOException;

}
