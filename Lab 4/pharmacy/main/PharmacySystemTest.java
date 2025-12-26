package pharmacy.main;

import pharmacy.contracts.Dispensable;
import pharmacy.contracts.Stockable;
import pharmacy.medications.AbstractMedication; 
import pharmacy.medications.OverTheCounterDrug;
import pharmacy.medications.PrescriptionDrug;

import java.util.ArrayList; 
import java.util.List;      
import java.util.Scanner;   

public class PharmacySystemTest {

    private static final Scanner scanner = new Scanner(System.in);
    
    private static final List<AbstractMedication> pharmacyInventory = new ArrayList<>();

    public static void main(String[] args) {
        
        while (true) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    createPrescriptionDrug();
                    break;
                case 2:
                    createOverTheCounterDrug();
                    break;
                case 3:
                    viewAllMedications();
                    break;
                case 4:
                    runDesignExplanation();
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
        System.out.println("\n--- Pharmacy Inventory System (Interfaces) ---");
        System.out.println("1. Add Prescription Drug");
        System.out.println("2. Add Over-The-Counter Drug");
        System.out.println("3. View All Medications & Dispense One");
        System.out.println("4. Run Explanation of Design Choices");
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


    private static void createPrescriptionDrug() {
        System.out.println("\n--- New Prescription Drug ---");
        String name = getStringInput("Enter Medication Name: ");
        String manuf = getStringInput("Enter Manufacturer: ");
        int stock = getIntInput("Enter Initial Stock: ");
        String dea = getStringInput("Enter Prescriber DEA Number: ");
        
        PrescriptionDrug p = new PrescriptionDrug(name, manuf, stock, dea);
        pharmacyInventory.add(p);
        System.out.println("SUCCESS: Prescription Drug created!");
    }

    private static void createOverTheCounterDrug() {
        System.out.println("\n--- New Over-The-Counter Drug ---");
        String name = getStringInput("Enter Medication Name: ");
        String manuf = getStringInput("Enter Manufacturer: ");
        int stock = getIntInput("Enter Initial Stock: ");
        String aisle = getStringInput("Enter Aisle Location (e.g., A5): ");
        
        OverTheCounterDrug o = new OverTheCounterDrug(name, manuf, stock, aisle);
        pharmacyInventory.add(o);
        System.out.println("SUCCESS: OTC Drug created!");
    }

    private static void viewAllMedications() {
        System.out.println("\n--- Viewing All Medications in Inventory ---");
        if (pharmacyInventory.isEmpty()) {
            System.out.println("Inventory is empty. Please add a medication first.");
            return;
        }

        for (AbstractMedication med : pharmacyInventory) {
            
            Dispensable d = (Dispensable) med;
            System.out.println("---------------------------------");
            System.out.println(d.getMedicationInfo()); // Polymorphic call

            Stockable s = (Stockable) med;
            System.out.println("  -> In Stock? " + s.isInStock());
            
            System.out.println("  ...Dispensing one unit to 'Test Customer'...");
            d.dispense("Test Customer");
            System.out.println("  -> New Stock Count: " + s.getStockCount());
        }
        System.out.println("---------------------------------");
        System.out.println("--- End of Inventory ---");
    }

    
    private static void runDesignExplanation() {
        System.out.println("\n\n--- EXPLANATION OF DESIGN CHOICES ---");
        System.out.println("The following explains the 'why' behind this lab's architecture.");

        explainDesignChoice(
            "Three Packages",
            "Why use 'contracts', 'medications', and 'main' packages?",
            "This is 'Separation of Concerns'. 'contracts' defines the API (the rules), 'medications' contains the implementation (the engine), and 'main' uses the code. This makes the project modular and easy to update."
        );

        explainDesignChoice(
            "Interfaces (Dispensable, Stockable)",
            "Why use interfaces instead of one big class?",
            "Flexibility. An object can implement multiple interfaces (e.g., Lipitor is both 'Dispensable' and 'Stockable'). It also lets us 'program to the interface' (e.g., 'Dispensable item1 = ...') to depend on a behavior, not a concrete class."
        );

        explainDesignChoice(
            "Abstract Class (AbstractMedication)",
            "Why add an 'abstract' class that wasn't required?",
            "Code Reuse (DRY Principle). Both 'PrescriptionDrug' and 'OverTheCounterDrug' need the same 'Stockable' logic. 'AbstractMedication' provides this logic in one place so we don't have to duplicate the code."
        );
        
        explainDesignChoice(
            "Encapsulation Pattern",
            "Why use 'private int stockLevel' and 'protected void adjustStock(int amount)'?",
            "This is a secure pattern. 'private' protects 'stockLevel' from being corrupted (e.g., set to -100). 'protected' gives subclasses a safe, controlled 'key' ('adjustStock') to modify that private data."
        );
    }
    
    public static void explainDesignChoice(String title, String question, String explanation) {
        System.out.println("\n[CHOICE: " + title + "]");
        System.out.println("  QUESTION: " + question);
        System.out.println("  RATIONALE: " + explanation);
    }
}