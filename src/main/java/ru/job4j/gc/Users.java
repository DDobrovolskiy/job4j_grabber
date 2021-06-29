package ru.job4j.gc;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class Users {
    @Param({ "100" })
    private int id;
    private String name = null;
    private final int test = 10;

    public Users(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Users() {
    }

    @Benchmark
    @BenchmarkMode(Mode.All) // тестируем во всех режимах
    @Warmup(iterations = 1) // число итераций для прогрева нашей функции
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Measurement(iterations = 2, batchSize = 2)
    public void measureName() {
        setTest();
    }

    public void setTest() {
        for (int i = 0; i < 100_000; i++) {
            id++;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Removed : " + id);
    }
}
