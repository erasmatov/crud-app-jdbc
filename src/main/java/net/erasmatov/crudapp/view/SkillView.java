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
            System.out.print("\nSKILL MENU:\n" +
                    "1. Add Skill\n" +
                    "2. Search Skill\n" +
                    "3. Update Skill\n" +
                    "4. Delete Skill\n" +
                    "5. Display All Skills\n" +
                    "9. Return to Main Menu\n" +
                    "0. Exit Program");
            System.out.print("\nEnter your selection > ");

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
                    System.out.print("\nEnter the Skill's ID for search: ");
                    Integer skillIdForSearch = input.nextInt();
                    Skill foundSkill = skillController.getSkillById(skillIdForSearch);

                    if (foundSkill.getId() != null) {
                        System.out.println("\nSkill has been found: " + foundSkill);
                    } else {
                        System.out.println("\nSkill has not been found.");
                    }
                    break;

                case 3:
                    showAllSkills();
                    System.out.print("\nEnter the Skill's ID for update: ");
                    Integer skillIdForUpdate = input.nextInt();
                    Skill updateSkill = skillController.getSkillById(skillIdForUpdate);
                    System.out.println("\n" + updateSkill);
                    input.nextLine();
                    System.out.print("\nSkill Name: ");
                    String updateSkillName = input.nextLine();

                    updateSkill.setName(updateSkillName);
                    updateSkill.setStatus(Status.ACTIVE);

                    if (updateSkill.getId() != null) {
                        skillController.updateSkill(updateSkill);
                        System.out.println("\nSkill has been updated: " + updateSkill);
                    } else {
                        System.out.println("\nSkill has not been updated.");
                    }
                    break;

                case 4:
                    showAllSkills();
                    System.out.print("\nEnter the Skill's ID for delete: ");
                    Integer skillIdForDelete = input.nextInt();
                    Skill deleteSkill = skillController.getSkillById(skillIdForDelete);

                    if (deleteSkill.getId() != null) {
                        skillController.deleteSkillById(skillIdForDelete);
                        System.out.println("\nSkill has been deleted: " + deleteSkill);
                    } else {
                        System.out.println("\nSkill has not been deleted.");
                    }
                    break;

                case 5:
                    showAllSkills();
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

    private void showAllSkills() {
        System.out.print("\nAll Skills: ");
        List<Skill> skillList = skillController.getSkills();
        for (Skill skill : skillList) {
            System.out.println(skill);
        }
    }

}
