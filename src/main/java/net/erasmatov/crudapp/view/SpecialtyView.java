package net.erasmatov.crudapp.view;

import net.erasmatov.crudapp.controller.SpecialtyController;
import net.erasmatov.crudapp.model.Specialty;
import net.erasmatov.crudapp.model.Status;

import java.util.List;
import java.util.Scanner;

public class SpecialtyView {
    private final SpecialtyController specialtyController = new SpecialtyController();
    private final Scanner input = new Scanner(System.in);

    public void showSpecialtyMenu() {
        int option;

        do {
            System.out.println("\nSPECIALTY MENU:\n" +
                    "1. Add Specialty\n" +
                    "2. Search Specialty\n" +
                    "3. Update Specialty\n" +
                    "4. Delete Specialty\n" +
                    "5. Display All Specialties\n" +
                    "9. Return to main menu\n" +
                    "0. Exit program");
            System.out.print("Enter your selection > ");

            option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1:
                    Specialty createSpecialty = new Specialty();
                    System.out.print("\nSpecialty Name: ");
                    String specialtyName = input.nextLine();

                    createSpecialty.setName(specialtyName);
                    createSpecialty.setStatus(Status.ACTIVE);

                    specialtyController.createSpecialty(createSpecialty);
                    System.out.println("\nSpecialty is created: " + createSpecialty);
                    break;

                case 2:
                    System.out.print("\nEnter Specialty's id for search: ");
                    Integer specialtyIdForSearch = input.nextInt();
                    Specialty foundSpecialty = specialtyController.getSpecialtyById(specialtyIdForSearch);
                    if (foundSpecialty.getId() != null) {
                        System.out.println("Specialty has been found: " + foundSpecialty);
                    } else {
                        System.out.println("Specialty has not been found.");
                    }
                    break;

                case 3:
                    System.out.println();
                    showAllSpecialties();
                    System.out.print("\nEnter Specialty's id for update: ");
                    Integer specialtyIdForUpdate = input.nextInt();
                    Specialty updateSpecialty = specialtyController.getSpecialtyById(specialtyIdForUpdate);
                    input.nextLine();
                    System.out.print("\nSpecialty Name: ");
                    String updateSpecialtyName = input.nextLine();

                    updateSpecialty.setName(updateSpecialtyName);
                    updateSpecialty.setStatus(Status.ACTIVE);

                    if (updateSpecialty.getId() != null) {
                        specialtyController.updateSpecialty(updateSpecialty);
                        System.out.println("Specialty has been updated: " + updateSpecialty);
                    } else {
                        System.out.println("Specialty has not been updated.");
                    }
                    break;

                case 4:
                    showAllSpecialties();
                    System.out.print("\nEnter Specialty's id for delete: ");
                    Integer specialtyIdForDelete = input.nextInt();
                    Specialty deleteSpecialty = specialtyController.getSpecialtyById(specialtyIdForDelete);
                    if (deleteSpecialty.getId() != null) {
                        specialtyController.deleteSpecialtyById(specialtyIdForDelete);
                        System.out.println("Specialty has been deleted: " + deleteSpecialty);
                    } else {
                        System.out.println("Specialty has not been deleted.");
                    }
                    break;

                case 5:
                    System.out.println("\nAll specialties:");
                    showAllSpecialties();
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

    private void showAllSpecialties() {
        List<Specialty> specilatyList = specialtyController.getSpecialties();
        for (Specialty specialty : specilatyList) {
            System.out.println(specialty);
        }
    }
}
