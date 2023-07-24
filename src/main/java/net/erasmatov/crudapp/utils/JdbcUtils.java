package net.erasmatov.crudapp.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils {
    private static Connection connection = null;

    private JdbcUtils() {
    }

    private static Connection getJdbcMysqlConnection() {

        if (connection == null) {
            final Properties PROPERTIES = new Properties();

            String URL = null;
            String USERNAME = null;
            String PASSWORD = null;

            try (InputStream fileInputStream = new FileInputStream("src/main/resources/application.properties")) {
                PROPERTIES.load(fileInputStream);
                URL = PROPERTIES.getProperty("url");
                USERNAME = PROPERTIES.getProperty("username");
                PASSWORD = PROPERTIES.getProperty("password");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static PreparedStatement getPreparedStatement(String sql) {
        try {
            return getJdbcMysqlConnection().prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static PreparedStatement getPreparedStatementWithKeys(String sql) {
        try {
            return getJdbcMysqlConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
