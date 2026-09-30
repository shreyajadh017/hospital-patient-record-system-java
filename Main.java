package hospital;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PatientManager manager = new PatientManager();

        while (true) {
            System.out.println("\n===== Hospital Patient Record System =====");
            System.out.println("1. Add Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Search Patient");
            System.out.println("4. Update Patient");
            System.out.println("5. Delete Patient");
            System.out.println("6. Add Medical History");
            System.out.println("7. View Medical History");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> manager.addPatient(sc);
                    case "2" -> manager.viewPatients();
                    case "3" -> manager.searchPatient(sc);
                    case "4" -> manager.updatePatient(sc);
                    case "5" -> manager.deletePatient(sc);
                    case "6" -> manager.addHistory(sc);
                    case "7" -> manager.viewHistory(sc);
                    case "8" -> { System.out.println("Thank you."); sc.close(); return; }
                    default -> System.out.println("Invalid choice. Please select 1-8.");
                }
            } catch (RuntimeException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
}
