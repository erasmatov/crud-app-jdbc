package net.erasmatov.crudapp.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtil {
    private static Connection connection = null;

    private JdbcUtil() {
    }

    public static Connection getJdbcMysqlConnection() {
        if (connection == null) {
            final Properties PROPERTIES = new Properties();
            try (InputStream fileInputStream = new FileInputStream("src/main/resources/application.properties")) {
                PROPERTIES.load(fileInputStream);
                String URL = PROPERTIES.getProperty("url");
                String USERNAME = PROPERTIES.getProperty("username");
                String PASSWORD = PROPERTIES.getProperty("password");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (IOException | SQLException e) {
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
