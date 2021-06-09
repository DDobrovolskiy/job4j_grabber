package ru.job4j.quartz;

import ru.job4j.connect.IProperties;

public class RabbitProperties implements IProperties {
    @Override
    public String getNameProperties() {
        return "rabbit.properties";
    }

    @Override
    public String getURL() {
        return "rabbit.url";
    }

    @Override
    public String getLogin() {
        return "rabbit.username";
    }

    @Override
    public String getPassword() {
        return "rabbit.password";
    }

    @Override
    public String getDriver() {
        return "rabbit.driver-class-name";
    }
}
