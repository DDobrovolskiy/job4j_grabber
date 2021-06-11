package ru.job4j.grabber;

import ru.job4j.connect.IProperties;

public class PsqlProperties implements IProperties {
    @Override
    public String getNameProperties() {
        return "app.properties";
    }

    @Override
    public String getURL() {
        return "jdbc.url";
    }

    @Override
    public String getLogin() {
        return "jdbc.username";
    }

    @Override
    public String getPassword() {
        return "jdbc.password";
    }

    @Override
    public String getDriver() {
        return "jdbc.driver-class-name";
    }
}
