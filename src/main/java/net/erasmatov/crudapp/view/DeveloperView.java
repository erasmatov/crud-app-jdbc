package net.erasmatov.crudapp.view;

import net.erasmatov.crudapp.controller.DeveloperController;
import net.erasmatov.crudapp.controller.SkillController;
import net.erasmatov.crudapp.controller.SpecialtyController;
import net.erasmatov.crudapp.model.Developer;
import net.erasmatov.crudapp.model.Skill;
import net.erasmatov.crudapp.model.Specialty;
import net.erasmatov.crudapp.model.Status;

import java.util.*;

public class DeveloperView {
    private final DeveloperController developerController = new DeveloperController();
    private final SkillController skillController = new SkillController();
    private final SpecialtyController specialtyController = new SpecialtyController();
    private final Scanner input = new Scanner(System.in);

    public void showDeveloperMenu() {
        int option;

        do {
            System.out.println("\nDEVELOPER MENU:\n" +
                    "1. Add Developer\n" +
                    "2. Search Developer\n" +
                    "3. Update Developer\n" +
                    "4. Delete Developer\n" +
                    "5. Display All Developers\n" +
                    "9. Return to Main Menu\n" +
                    "0. Exit program");
            System.out.print("Enter your selection > ");

            option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1:
                    Developer createDeveloper = new Developer();
                    System.out.print("\nFirstName: ");
                    String firstName = input.nextLine();

                    System.out.print("\nLastName: ");
                    String lastName = input.nextLine();
                    System.out.println();

                    List<Skill> skills = setSkillsForDeveloper();

                    showSpecialtiesForDeveloper();
                    System.out.print("Select Specialty by ID > ");
                    Integer specialtyId = input.nextInt();
                    Specialty specialty = specialtyController.getSpecialtyById(specialtyId);

                    createDeveloper.setFirstName(firstName);
                    createDeveloper.setLastName(lastName);
                    createDeveloper.setSkills(skills);
                    createDeveloper.setSpecialty(specialty);
                    createDeveloper.setStatus(Status.ACTIVE);
                    developerController.createDeveloper(createDeveloper);
                    System.out.println("\nDeveloper is created: " + createDeveloper);
                    break;

                case 2:
                    System.out.print("\nEnter Developer's ID for search: ");
                    Integer developerIdForSearch = input.nextInt();
                    Developer foundDeveloper = developerController.getDeveloperById(developerIdForSearch);

                    if (foundDeveloper.getId() != null) {
                        System.out.println("Developer has been found: " + foundDeveloper);
                    } else {
                        System.out.println("Developer has not been found.");
                    }
                    break;

                case 3:
                    showAllDevelopers();
                    System.out.print("\nEnter Developer's ID for update: ");
                    Integer developerIdForUpdate = input.nextInt();
                    Developer updateDeveloper = developerController.getDeveloperById(developerIdForUpdate);
                    input.nextLine();

                    System.out.print("\nFirstName: ");
                    String updateFirstName = input.nextLine();

                    System.out.print("\nLastName: ");
                    String updateLastName = input.nextLine();
                    System.out.println();

                    List<Skill> updateSkills = setSkillsForDeveloper();
                    showSpecialtiesForDeveloper();
                    System.out.print("Select Specialty by ID > ");
                    Integer updateSpecialtyId = input.nextInt();
                    Specialty updateSpecialty = specialtyController.getSpecialtyById(updateSpecialtyId);

                    updateDeveloper.setFirstName(updateFirstName);
                    updateDeveloper.setLastName(updateLastName);
                    updateDeveloper.setSkills(updateSkills);
                    updateDeveloper.setSpecialty(updateSpecialty);
                    updateDeveloper.setStatus(Status.ACTIVE);

                    if (updateDeveloper.getId() != null) {
                        developerController.updateDeveloper(updateDeveloper);
                        System.out.println("\nDeveloper has been updated: " + updateDeveloper);
                    } else {
                        System.out.println("\nDeveloper has not been updated.");
                    }
                    break;

                case 4:
                    showAllDevelopers();
                    System.out.print("\nEnter Developer's ID for delete: ");
                    Integer developerIdForDelete = input.nextInt();
                    Developer deleteDeveloper = developerController.getDeveloperById(developerIdForDelete);

                    if (deleteDeveloper.getId() != null) {
                        developerController.deleteDeveloperById(developerIdForDelete);
                        System.out.println("\nDeveloper has been deleted: " + deleteDeveloper);
                    } else {
                        System.out.println("\nDeveloper has not been deleted.");
                    }
                    break;

                case 5:
                    showAllDevelopers();
                    break;

                case 9:
                    return;

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

    private List<Skill> setSkillsForDeveloper() {
        showSkillsForDeveloper();
        List<Skill> skills = new ArrayList<>();

        while (true) {
            if (!skills.isEmpty()) {
                System.out.println("\n" + skills);
            }
            System.out.print("Select Skills by ID, enter 0 to finish. > ");
            int idSkill = input.nextInt();
            if (idSkill > 0) {
                Skill skill = skillController.getSkillById(idSkill);
                skills.add(skill);
            } else if (idSkill == 0) {
                System.out.println("\nSelected skills:\n" + skills + "\n");
                return skills;
            } else {
                System.out.print("\nInvalid input: " + idSkill + "!\n" +
                        "Please enter a valid choice...\n");
            }
        }
    }

    private void showSkillsForDeveloper() {
        List<Skill> skillList = skillController.getSkills();
        for (Skill skill : skillList) {
            System.out.println(skill);
        }
    }

    private void showSpecialtiesForDeveloper() {
        List<Specialty> specialtiesList = specialtyController.getSpecialties();
        for (Specialty specialty : specialtiesList) {
            System.out.println(specialty);
        }
    }

    private void showAllDevelopers() {
        System.out.print("\nAll Developers: ");
        List<Developer> developersList = developerController.getAllDevelopers();
        for (Developer developer : developersList) {
            System.out.println(developer);
        }
    }

}
