package ru.job4j.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectSQL implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectSQL.class.getName());
    private Connection connection;
    private ConfigSQL config;

    public ConnectSQL(ConfigSQL config) {
        this.config = config;
        init(config);
    }

    private void init(ConfigSQL config) {
        try {
            Class.forName(config.getDriver());
            connection = DriverManager.getConnection(
                    config.getURL(),
                    config.getLogin(),
                    config.getPassword()
            );
        } catch (Exception e) {
            LOG.error("Error load connection: ", e);
        }
    }

    public Connection get() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
