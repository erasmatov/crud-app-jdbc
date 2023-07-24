package net.erasmatov.crudapp.view;

import net.erasmatov.crudapp.controller.SkillController;
import net.erasmatov.crudapp.model.Skill;
import net.erasmatov.crudapp.model.Status;

import java.util.List;
import java.util.Scanner;

public class SkillView {
    private final SkillController skillController = new SkillController();
    private final Scanner input = new Scanner(System.in);

    public void showSkillMenu() {
        int option;

        do {
            System.out.println("\nSKILL MENU:\n" +
                    "1. Add Skill\n" +
                    "2. Search Skill\n" +
                    "3. Update Skill\n" +
                    "4. Delete Skill\n" +
                    "5. Display All Skills\n" +
                    "9. Return to main menu\n" +
                    "0. Exit program");
            System.out.print("Enter your selection > ");

            option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1:
                    Skill createSkill = new Skill();
                    System.out.print("\nSkill Name: ");
                    String skillName = input.nextLine();

                    createSkill.setName(skillName);
                    createSkill.setStatus(Status.ACTIVE);

                    skillController.createSkill(createSkill);
                    System.out.println("\nSkill is created: " + createSkill);
                    break;

                case 2:
                    System.out.print("\nEnter Skill's id for search: ");
                    Integer skillIdForSearch = input.nextInt();
                    Skill foundSkill = skillController.getSkillById(skillIdForSearch);
                    if (foundSkill.getId() != null) {
                        System.out.println("Skill has been found: " + foundSkill);
                    } else {
                        System.out.println("Skill has not been found.");
                    }
                    break;

                case 3:
                    showAllSkills();
                    System.out.print("\nEnter Skill's id for update: ");
                    Integer skillIdForUpdate = input.nextInt();
                    Skill updateSkill = skillController.getSkillById(skillIdForUpdate);
                    input.nextLine();
                    System.out.print("\nSkill Name: ");
                    String updateSkillName = input.nextLine();

                    updateSkill.setName(updateSkillName);
                    updateSkill.setStatus(Status.ACTIVE);

                    if (updateSkill.getId() != null) {
                        skillController.updateSkill(updateSkill);
                        System.out.println("Skill has been updated: " + updateSkill);
                    } else {
                        System.out.println("Skill has not been updated.");
                    }
                    break;

                case 4:
                    showAllSkills();
                    System.out.print("\nEnter Skill's id for delete: ");
                    Integer skillIdForDelete = input.nextInt();
                    Skill deleteSkill = skillController.getSkillById(skillIdForDelete);
                    if (deleteSkill.getId() != null) {
                        skillController.deleteSkillById(skillIdForDelete);
                        System.out.println("Skill has been deleted: " + deleteSkill);
                    } else {
                        System.out.println("Skill has not been deleted.");
                    }
                    break;

                case 5:
                    System.out.println("\nAll skills:");
                    showAllSkills();
                    break;

                case 9:
                    System.out.println();
                    return;

                case 0:
                    System.out.print(
                            "\nThank you for using the program. Goodbye!");
                    input.close();
                    break;

                default:
                    System.out.println("Invalid input: " + option + "!\n");
                    break;
            }

        } while (option != 0);
        System.exit(0);
    }

    private void showAllSkills() {
        List<Skill> skillList = skillController.getSkills();
        for (Skill skill : skillList) {
            System.out.println(skill);
        }
    }
}
