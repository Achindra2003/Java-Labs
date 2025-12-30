package main;

import data.InventoryRepository;
import model.Medicine;
import exception.PharmacyStorageException;
// We import RuntimeExceptions implicitly, but can be explicit for clarity
import exception.BusinessRuleException; 

import java.util.Map;
import java.util.Scanner;

public class PharmacyApp {
    private static Map<String, Medicine> inventory;

    public static void main(String[] args) {
        // Assume data is in a folder named 'pharmacy_data'
        // Structure: 
        // pharmacy_data/antibiotics/data.csv
        // pharmacy_data/painkillers/data.csv
        InventoryRepository repo = new InventoryRepository("pharmacy_data");
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Pharmacy System 2.0 (Multi-File) ===");

        // --- PHASE 1: LOADING (Checked Exceptions) ---
        try {
            // Self-Learning: Audit files before loading
            repo.auditDirectoryStructure();
            
            // Load actual data
            inventory = repo.loadInventory();
            System.out.println(">> System Loaded. Total items: " + inventory.size());

        } catch (PharmacyStorageException e) {
            // IO/Access Errors are fatal here
            System.err.println("FATAL STARTUP ERROR: " + e.getMessage());
            if (e.getCause() != null) System.err.println("Cause: " + e.getCause());
            return; // Terminate
        }

        // --- PHASE 2: OPERATION (Unchecked Exceptions) ---
        while (true) {
            System.out.println("\n[1] View Stock  [2] Sell Item  [3] Exit");
            System.out.print("> ");
            String choice = scanner.next();

            if (choice.equals("3")) break;

            if (choice.equals("1")) {
                inventory.values().forEach(System.out::println);
            } else if (choice.equals("2")) {
                handleTransaction(scanner);
            }
        }
        scanner.close();
    }

    private static void handleTransaction(Scanner scanner) {
        try {
            System.out.print("Medicine ID: ");
            String id = scanner.next();
            
            // Validate Existence
            if (!inventory.containsKey(id)) {
                throw new BusinessRuleException("ID " + id + " not found.");
            }

            System.out.print("Quantity: ");
            if (!scanner.hasNextInt()) {
                scanner.next(); // clear buffer
                // Throwing standard RuntimeException for bad input
                throw new IllegalArgumentException("Quantity must be a number.");
            }
            int qty = scanner.nextInt();

            // Business Logic Validation
            Medicine med = inventory.get(id);
            if (qty <= 0) throw new IllegalArgumentException("Quantity cannot be negative.");
            if (med.isExpired()) throw new BusinessRuleException("Item is EXPIRED.");
            if (med.getQuantity() < qty) throw new BusinessRuleException("Insufficient Stock.");

            // Success
            med.reduceStock(qty);
            System.out.printf("Sold %d of %s. Total: $%.2f%n", qty, med.getName(), (qty * med.getPrice()));

        } catch (BusinessRuleException e) {
            // Logic Error: User tried something valid syntactically but invalid commercially
            System.out.println(">> TRANSACTION DENIED: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Input Error: User typed garbage
            System.out.println(">> INPUT ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(">> UNEXPECTED ERROR: " + e.toString());
        }
    }
}