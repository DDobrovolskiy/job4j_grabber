package ru.job4j.gc;

public class Users {

    private final int id;
    private final String name;
    private final int test = 10;

    public Users(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Removed : " + id);
    }
}
