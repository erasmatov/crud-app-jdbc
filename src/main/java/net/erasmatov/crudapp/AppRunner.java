package net.erasmatov.crudapp;

import net.erasmatov.crudapp.utils.LiquibaseUtil;
import net.erasmatov.crudapp.view.MainView;

public class AppRunner {
    public static void main(String[] args) {
        LiquibaseUtil.liquibaseMigrate();

        MainView mainView = new MainView();
        mainView.showMainMenu();
    }
}