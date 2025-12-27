package main;

import pharmacy.MedicationStock;
import pharmacy.tasks.InventoryRestocker;
import pharmacy.tasks.PrescriptionFiller;
import pharmacy.tasks.SystemAuditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PharmacySimulation {

    public static volatile boolean isPharmacyOpen = true;
    private static final Scanner scanner = new Scanner(System.in);
    
    private static final List<Thread> activeThreads = new ArrayList<>();
    private static SystemAuditor auditor; // We'll just have one auditor
    private static MedicationStock aspirinStock;

    public static void main(String[] args) {
        
        System.out.println("--- Pharmacy Multithreading Simulation ---");
        
        int initialStock = getIntInput("Enter initial stock for Aspirin: ", 0, 500);
        int maxStock = getIntInput("Enter max stock for Aspirin: ", initialStock, 1000);
        aspirinStock = new MedicationStock("Aspirin", initialStock, maxStock);
        
        while (isPharmacyOpen) {
            printMenu();
            int choice = getIntInput("Enter your choice: ", 0, 6);

            switch (choice) {
                case 1:
                    startPharmacist();
                    break;
                case 2:
                    startRestocker();
                    break;
                case 3:
                    startOrResumeAuditor();
                    break;
                case 4:
                    pauseAuditor();
                    break;
                case 5:
                    viewCurrentStock();
                    break;
                case 0:
                    shutdownSimulation();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Simulation has ended. Goodbye.");
        scanner.close();
    }
    
    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Start new Pharmacist thread (Consumer)");
        System.out.println("2. Start new Restocker thread (Producer)");
        System.out.println("3. Start/Resume Auditor thread");
        System.out.println("4. Pause Auditor thread");
        System.out.println("5. View Current Stock");
        System.out.println("0. STOP SIMULATION");
    }

    private static void startPharmacist() {
        String name = "Pharmacist-" + (activeThreads.size() + 1);
        int fillAmount = getIntInput("Enter fill amount for " + name + ": ", 1, 50);
        
        PrescriptionFiller fillerTask = new PrescriptionFiller(name, aspirinStock, fillAmount);
        Thread pharmacistThread = new Thread(fillerTask, name);
        
        activeThreads.add(pharmacistThread);
        pharmacistThread.start();
        System.out.println("Started " + name + " trying to fill " + fillAmount + " units.");
    }

    private static void startRestocker() {
        String name = "Restocker-" + (activeThreads.size() + 1);
        int restockAmount = getIntInput("Enter restock amount for " + name + ": ", 1, 100);

        InventoryRestocker restockThread = new InventoryRestocker(name, aspirinStock, restockAmount);

        activeThreads.add(restockThread);
        restockThread.start();
        System.out.println("Started " + name + " trying to restock " + restockAmount + " units.");
    }

    private static void startOrResumeAuditor() {
        if (auditor == null) {
            System.out.println("Starting new Auditor thread...");
            auditor = new SystemAuditor(aspirinStock);
            Thread auditorThread = new Thread(auditor, "System-Auditor");
            activeThreads.add(auditorThread);
            auditorThread.start();
        } else {
            System.out.println("Resuming Auditor...");
            auditor.resume();
        }
    }

    private static void pauseAuditor() {
        if (auditor != null) {
            auditor.pause();
        } else {
            System.out.println("Auditor is not running.");
        }
    }

    private static void viewCurrentStock() {
        int stock = aspirinStock.getStockLevel();
        System.out.println(">>> Current " + aspirinStock.getMedicationName() + " stock is: " + stock);
    }

    private static void shutdownSimulation() {
        System.out.println("SHUTTING DOWN... Signaling all threads to stop.");
        isPharmacyOpen = false; // Set the volatile flag

        if (auditor != null) {
            auditor.resume();
        }

        for (Thread t : activeThreads) {
            if (t.isAlive()) {
                t.interrupt();
            }
        }
        System.out.println("All threads have been signaled.");
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getIntInput(String prompt, int min, int max) {
        int value = -1;
        while (true) {
            try {
                System.out.print(prompt + " (" + min + "-" + max + "): ");
                value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Invalid range. Please enter a value between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }
}