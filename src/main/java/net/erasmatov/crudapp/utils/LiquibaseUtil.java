package net.erasmatov.crudapp.utils;

import liquibase.command.CommandScope;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionCommandStep;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class LiquibaseUtil {
    public static void liquibaseMigrate() {
        final Properties PROPERTIES = new Properties();
        final Connection connection;

        String URL;
        String USERNAME;
        String PASSWORD;

        try (InputStream fileInputStream = new FileInputStream("src/main/resources/application.properties")) {
            PROPERTIES.load(fileInputStream);
            URL = PROPERTIES.getProperty("url");
            USERNAME = PROPERTIES.getProperty("username");
            PASSWORD = PROPERTIES.getProperty("password");

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            CommandScope updateCommand = new CommandScope(UpdateCommandStep.COMMAND_NAME);
            updateCommand.addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database);
            updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, "db/changelog/changelog-main.xml");
            updateCommand.execute();
            connection.close();
        } catch (LiquibaseException | IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
