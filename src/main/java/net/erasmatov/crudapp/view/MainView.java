package net.erasmatov.crudapp.view;

import java.util.Scanner;

public class MainView {
    private final DeveloperView developerView;
    private final SkillView skillView;
    private final SpecialtyView specialtyView;
    private final Scanner input = new Scanner(System.in);

    public MainView() {
        developerView = new DeveloperView();
        skillView = new SkillView();
        specialtyView = new SpecialtyView();
    }

    public void showMainMenu() {
        int option;

        do {
            System.out.print("\nMAIN MENU:\n" +
                    "1. Developer\n" +
                    "2. Skill\n" +
                    "3. Specialty\n" +
                    "0. Exit Program");
            System.out.print("\nEnter your selection > ");

            option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1:
                    developerView.showDeveloperMenu();
                    break;

                case 2:
                    skillView.showSkillMenu();
                    break;

                case 3:
                    specialtyView.showSpecialtyMenu();
                    break;

                case 0:
                    System.out.print(
                            "\nThank you for using the program. Goodbye!");
                    break;

                default:
                    System.out.print("\nInvalid input: " + option + "!\n" +
                            "Please enter a valid choice...\n");
            }
        } while (option != 0);
        input.close();
        System.exit(0);
    }

}