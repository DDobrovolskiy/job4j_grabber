package ru.job4j.quartz;

import ru.job4j.connect.ConfigSQL;

import java.util.Properties;

public class RabbitProperties implements ConfigSQL {
    private Properties properties;

    public RabbitProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String getURL() {
        return properties.getProperty("rabbit.url");
    }

    @Override
    public String getLogin() {
        return properties.getProperty("rabbit.username");
    }

    @Override
    public String getPassword() {
        return properties.getProperty("rabbit.password");
    }

    @Override
    public String getDriver() {
        return properties.getProperty("rabbit.driver-class-name");
    }
}
