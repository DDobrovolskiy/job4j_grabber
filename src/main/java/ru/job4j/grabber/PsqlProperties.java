package ru.job4j.grabber;

import ru.job4j.connect.IConfigSQL;

import java.util.Properties;

public class PsqlProperties implements IConfigSQL {
    private Properties properties;

    public PsqlProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String getURL() {
        return properties.getProperty("jdbc.url");
    }

    @Override
    public String getLogin() {
        return properties.getProperty("jdbc.username");
    }

    @Override
    public String getPassword() {
        return properties.getProperty("jdbc.password");
    }

    @Override
    public String getDriver() {
        return properties.getProperty("jdbc.driver-class-name");
    }
}
