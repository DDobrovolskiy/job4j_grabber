package ru.job4j.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectSQL implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectSQL.class.getName());
    private Connection connection;

    public Connection get(IProperties iProperties) {
        try (InputStream in = ConnectSQL
                .class
                .getClassLoader()
                .getResourceAsStream(iProperties.getNameProperties())) {
            Properties config = new Properties();
            if (in == null) {
                LOG.error("Ошибка загрузки файла свойств, стрим пуст! - ",
                        iProperties.getNameProperties());
            }
            config.load(in);
            connection = DriverManager.getConnection(
                    config.getProperty(iProperties.getURL()),
                    config.getProperty(iProperties.getLogin()),
                    config.getProperty(iProperties.getPassword())
            );
        } catch (Exception e) {
            LOG.error("Error load properties and connection: ", e);
        }
        return connection;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
