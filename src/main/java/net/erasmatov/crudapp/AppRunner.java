package net.erasmatov.crudapp;

import net.erasmatov.crudapp.utils.FlywayUtil;
import net.erasmatov.crudapp.view.MainView;

public class AppRunner {
    public static void main(String[] args) {
        FlywayUtil.flywayMigrate();

        MainView mainView = new MainView();
        mainView.showMainMenu();
    }
}