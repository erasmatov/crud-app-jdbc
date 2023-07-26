package net.erasmatov.crudapp.utils;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

public class LiquibaseUtil {
    public static void liquibaseMigrate() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/database22";
            String username = "root";
            String password = "password";
            Connection connection = DriverManager.getConnection(url, username, password);

            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase("db/changelog/changelog-main.xml",
                    new ClassLoaderResourceAccessor(), database);

            liquibase.update("");

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
