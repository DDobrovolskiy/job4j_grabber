package ru.job4j.gc.cache;

import java.io.*;

public class DirFileCache extends AbstractCache<String, String> {

    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
        try (var inStream = new BufferedReader(new FileReader(cachingDir + "\\" + key))) {
            System.out.println("Загрузка по адресу: " + cachingDir + "\\" + key);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(inStream);
            put(key, stringBuilder.toString());
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}