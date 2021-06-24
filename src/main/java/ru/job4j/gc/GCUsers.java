package ru.job4j.gc;

import static com.carrotsearch.sizeof.RamUsageEstimator.sizeOf;

public class GCUsers {
    private static final long KB = 1000;
    private static final long MB = KB * KB;
    private static final Runtime ENVIRONMENT = Runtime.getRuntime();

    public static void info() {
        final long freeMemory = ENVIRONMENT.freeMemory();
        final long totalMemory = ENVIRONMENT.totalMemory();
        final long maxMemory = ENVIRONMENT.maxMemory();
        System.out.println("=== Environment state ===");
        System.out.printf("Free: %d%n", freeMemory);
        System.out.printf("Total: %d%n", totalMemory);
        System.out.printf("Max: %d%n", maxMemory);
    }

    public static void main(String[] args) {
        // пустой класс без полей занимает 16 бит
        long classNoField = sizeOf(new ClassNoField());
        // класс с двумя полями занимает 72 бита
        long classWithTwoField = sizeOf(new Users(0, "Name"));
        info();
        /**
         * -Xmx5m : Устанавливает максимальный размер кучи (5 мбайт).
         * -Xms5m : Устанавливает начальный размер кучи при запуске JVM (5 мбайт).
         * -Xmn1m : Устанавливает размер молодого поколения (1 мбайт).
         *
         * Засчет уменьшения размера кучи можно добиться вызова сборщика муса значительно
         * чаще чем обычно (невсегда полезно).
         */
        for (int i = 0; i < 3000; i++) {
            new Users(i, "N" + i);
            //System.gc();
        }
        /**
         * В данном случае создается множество классов Users, до которых не возможно добраться
         * из root, они размещаются в куче в области Young (Eden), как только область
         * памяти начинает заканчиваться вызывается GC. GC определяет какие объекты достижимы,
         * а какие нет и удаляет те объекты, которые не достижимы, а достижимые копирует
         * в survivor область. Чем больше проходов "выживает" объект после прохода GC
         * тем старше он становится и после нескольких итераций GC отправляется в Old область
         */
        //System.gc();
        info();
    }
}
