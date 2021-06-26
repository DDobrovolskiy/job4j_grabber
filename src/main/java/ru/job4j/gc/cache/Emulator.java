package ru.job4j.gc.cache;

import java.io.IOException;
import java.util.Scanner;

//-Xmx5m
public class Emulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DirFileCache dirFileCache = null;
        String dir = null;
        boolean flag = true;
        while (flag) {
            System.out.println("----------");
            String string = scanner.nextLine();
            if (string.equals("close")) {
                flag = false;
            } else if (string.startsWith("dir ")) {
                dir = string.split(" ")[1];
                dirFileCache = new DirFileCache(dir);
                System.out.println("Directory: " + dir);
            } else if (string.startsWith("load ")) {
                if (dir != null) {
                    dirFileCache.load(string.split(" ")[1]);
                } else {
                    System.out.println("Директория не укзана!");
                }
            } else if (string.startsWith("gc")) {
                System.out.println("Запуск GC");
                System.gc();
            } else {
                try {
                    if (dir != null) {
                        String result = dirFileCache.get(string);
                    } else {
                        System.out.println("Директория не укзана!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
