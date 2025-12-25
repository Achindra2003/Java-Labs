package main;

import pharmacy.ControlledSubstancePrescription;
import pharmacy.RefillablePrescription;
import pharmacy.ChronicCarePrescription;
import pharmacy.Prescription; 
import pharmacy.PrescriptionValidator;

import java.util.ArrayList;
import java.util.List;      
import java.util.Scanner;  

public class PharmacySystem {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Prescription> pharmacyInventory = new ArrayList<>();

    public static void main(String[] args) {
        
        while (true) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    createRefillablePrescription();
                    break;
                case 2:
                    createControlledPrescription();
                    break;
                case 3:
                    createChronicCarePrescription();
                    break;
                case 4:
                    viewAllPrescriptions();
                    break;
                case 5:
                    runExplanationScenarios();
                    break;
                case 0:
                    System.out.println("Exiting Pharmacy System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void printMenu() {
        System.out.println("\n--- Pharmacy Management System ---");
        System.out.println("1. Create Refillable Prescription");
        System.out.println("2. Create Controlled Substance Prescription");
        System.out.println("3. Create Chronic Care Prescription");
        System.out.println("4. View All Prescriptions in Inventory");
        System.out.println("5. Run Explanation of Java Rules");
        System.out.println("0. Exit");
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }


    private static void createRefillablePrescription() {
        System.out.println("\n--- New Refillable Prescription ---");
        String patient = getStringInput("Enter Patient Name: ");
        String med = getStringInput("Enter Medication Name: ");
        int id = getIntInput("Enter Prescription ID: ");
        int refills = getIntInput("Enter Number of Refills: ");
        
        RefillablePrescription p = new RefillablePrescription(patient, med, id, refills);
        pharmacyInventory.add(p);
        System.out.println("SUCCESS: Refillable Prescription created!");
    }

    private static void createControlledPrescription() {
        System.out.println("\n--- New Controlled Substance Prescription ---");
        String patient = getStringInput("Enter Patient Name: ");
        String med = getStringInput("Enter Medication Name: ");
        int id = getIntInput("Enter Prescription ID: ");
        String dea = getStringInput("Enter Prescriber DEA Number: ");
        
        ControlledSubstancePrescription p = new ControlledSubstancePrescription(patient, med, id, dea);
        pharmacyInventory.add(p);
        System.out.println("SUCCESS: Controlled Substance Prescription created!");
    }
    
    private static void createChronicCarePrescription() {
        System.out.println("\n--- New Chronic Care Prescription ---");
        String patient = getStringInput("Enter Patient Name: ");
        String med = getStringInput("Enter Medication Name: ");
        int id = getIntInput("Enter Prescription ID: ");
        int refills = getIntInput("Enter Number of Refills: ");
        String code = getStringInput("Enter Diagnosis Code (e.g., E11.9): ");
        
        ChronicCarePrescription p = new ChronicCarePrescription(patient, med, id, refills, code);
        pharmacyInventory.add(p);
        System.out.println("SUCCESS: Chronic Care Prescription created!");
    }


    private static void viewAllPrescriptions() {
        System.out.println("\n--- Viewing All Prescriptions in Inventory ---");
        if (pharmacyInventory.isEmpty()) {
            System.out.println("Inventory is empty. Please create a prescription first.");
            return;
        }

        for (Prescription p : pharmacyInventory) {
            p.displayPrescriptionDetails(); // Polymorphic call
            p.printPharmacyLabel();     // Final method call (inherited by all)
            
            // Validate the ID
            boolean isValid = PrescriptionValidator.isIdFormatValid(p.getPrescriptionID());
            System.out.println("Is ID " + p.getPrescriptionID() + " valid? " + isValid);
        }
        System.out.println("--- End of Inventory ---");
    }
    
    private static void runExplanationScenarios() {
        System.out.println("\n\n--- EXPLANATION OF COMPILE-TIME RULES ---");
        System.out.println("The following scenarios test rules that stop the program from compiling.");
        System.out.println("We will run each test scenario and explain the expected compile-time error.");

        // Rule 1: 'final' variable
        runCompilationTestScenario(
            "Final Variable",
            "Attempt to change 'oxycodone.PHARMACY_NAME = \"New Pharmacy\";'",
            "cannot assign a value to final variable PHARMACY_NAME"
        );

        // Rule 2: 'final' method
        runCompilationTestScenario(
            "Final Method",
            "Attempt to override 'printPharmacyLabel()' in 'RefillablePrescription'",
            "overridden method is final"
        );

        // Rule 3: 'final' class
        runCompilationTestScenario(
            "Final Class",
            "Attempt to write 'class ExtendedValidator extends PrescriptionValidator'",
            "cannot inherit from final PrescriptionValidator"
        );

        // Rule 4: 'super()' constructor call
        runCompilationTestScenario(
            "Super() Constructor",
            "Attempt to put a print statement before 'super()' in a subclass constructor",
            "call to super must be first statement in constructor"
        );
    }

    public static void runCompilationTestScenario(String testName, String scenario, String expectedError) {
        System.out.println("\n[SCENARIO: " + testName + "]");
        System.out.println("  TEST: " + scenario);
        System.out.println("  RESULT: This code will not compile.");
        System.out.println("  EXPECTED ERROR: '" + expectedError + "'");
    }
}