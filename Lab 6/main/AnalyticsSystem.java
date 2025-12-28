package main;

import pharmacy.analytics.AnalyticsOperation;
import pharmacy.analytics.DomainDataAnalyzer;
import pharmacy.analytics.DomainComparator;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;

public class AnalyticsSystem {

    private static final DomainDataAnalyzer<String, Double> costAnalyzer = new DomainDataAnalyzer<>("Prescription Costs");
    private static final DomainDataAnalyzer<String, Integer> refillAnalyzer = new DomainDataAnalyzer<>("Patient Refill Counts");
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("--- Pharmacy Domain Data Analyzer ---");
        
        while (true) {
            printMainMenu();
            int choice = getIntInput("Enter your choice: ", 0, 2);

            switch (choice) {
                case 1:
                    manageCostData();
                    break;
                case 2:
                    manageRefillData();
                    break;
                case 0:
                    System.out.println("Exiting analyzer. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("Select a dataset to manage:");
        System.out.println("1. Prescription Costs ($ USD) [Type: <String, Double>]");
        System.out.println("2. Patient Refill Counts [Type: <String, Integer>]");
        System.out.println("0. Exit");
    }

    // --- 1. PRESCRIPTION COST (DOUBLE) MANAGEMENT ---

    private static void manageCostData() {
        while (true) {
            System.out.println("\n--- Managing: " + costAnalyzer.getDataName() + " ---");
            System.out.println("1. Add a new prescription cost");
            System.out.println("2. View all costs");
            System.out.println("3. Run Cost Analytics");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ", 0, 3);
            switch (choice) {
                case 1:
                    String medName = getStringInput("Enter Medication Name (Key): ");
                    double cost = getDoubleInput("Enter prescription cost (Value): ");
                    costAnalyzer.addData(medName, cost);
                    break;
                case 2:
                    costAnalyzer.displayData();
                    break;
                case 3:
                    runCostAnalytics();
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void runCostAnalytics() {
        if (costAnalyzer.isEmpty()) {
            System.out.println("No data to analyze. Please add costs first.");
            return;
        }
        
        System.out.println("\n--- " + costAnalyzer.getDataName() + " Analytics ---");
        
        AnalyticsOperation<Double> totalCost = values -> 
            values.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        
        AnalyticsOperation<Double> averageCost = values -> 
            values.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        
        double highCostThreshold = 100.0;
        Predicate<Double> highCostFilter = cost -> cost > highCostThreshold;
        
        DomainComparator<Double> isMoreExpensive = (cost, threshold) -> cost > threshold;
        double targetCost = 150.0;
        
        System.out.printf("  Total Cost of all Prescriptions: $%.2f%n", costAnalyzer.performAnalysis(totalCost));
        System.out.printf("  Average Prescription Cost:       $%.2f%n", costAnalyzer.performAnalysis(averageCost));
        
        System.out.println("\n  --- Initiative: High-Cost Filter (>$" + highCostThreshold + ") ---");
        Map<String, Double> highCostMeds = costAnalyzer.filterByValue(highCostFilter);
        if (highCostMeds.isEmpty()) {
            System.out.println("  [No medications found matching this filter]");
        } else {
            highCostMeds.forEach((name, cost) -> 
                System.out.printf("  - %s: $%.2f%n", name, cost)
            );
        }
        
        Optional<Map.Entry<String, Double>> firstExpensive = costAnalyzer.findFirstMatch(isMoreExpensive, targetCost);
        System.out.println("\n  First Cost Found Over $" + targetCost + ": " + 
            firstExpensive.map(entry -> entry.getKey() + " ($" + entry.getValue() + ")").orElse("None"));
    }

    // --- 2. PATIENT REFILL (INTEGER) MANAGEMENT ---

    private static void manageRefillData() {
        while (true) {
            System.out.println("\n--- Managing: " + refillAnalyzer.getDataName() + " ---");
            System.out.println("1. Add a new patient refill count");
            System.out.println("2. View all refill counts");
            System.out.println("3. Run Refill Analytics");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ", 0, 3);
            switch (choice) {
                case 1:
                    String patientName = getStringInput("Enter Patient Name (Key): ");
                    int refills = getIntInput("Enter total refills (Value): ", 0, Integer.MAX_VALUE);
                    refillAnalyzer.addData(patientName, refills);
                    break;
                case 2:
                    refillAnalyzer.displayData();
                    break;
                case 3:
                    runRefillAnalytics();
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void runRefillAnalytics() {
        if (refillAnalyzer.isEmpty()) {
            System.out.println("No data to analyze. Please add refill counts first.");
            return;
        }
        
        System.out.println("\n--- " + refillAnalyzer.getDataName() + " Analytics ---");
        
        AnalyticsOperation<Integer> totalRefills = values -> 
            values.stream()
                .mapToDouble(Integer::doubleValue)
                .sum();
        
        AnalyticsOperation<Integer> averageRefills = values -> 
            values.stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0.0);
        
        int chronicThreshold = 5;
        Predicate<Integer> chronicFilter = count -> count > chronicThreshold;

        DomainComparator<Integer> isZero = (count, target) -> count.equals(target);
        int targetRefills = 0;
        
        System.out.printf("  Total Refills Issued:          %.0f%n", refillAnalyzer.performAnalysis(totalRefills));
        System.out.printf("  Average Refill Count:          %.2f%n", refillAnalyzer.performAnalysis(averageRefills));
        
        System.out.println("\n  --- Initiative: Chronic Care Filter (>" + chronicThreshold + " refills) ---");
        Map<String, Integer> chronicPatients = refillAnalyzer.filterByValue(chronicFilter);
        if (chronicPatients.isEmpty()) {
            System.out.println("  [No patients found matching this filter]");
        } else {
            chronicPatients.forEach((name, count) -> 
                System.out.println("  - " + name + ": " + count + " refills")
            );
        }
        
        Optional<Map.Entry<String, Integer>> firstZero = refillAnalyzer.findFirstMatch(isZero, targetRefills);
        System.out.println("\n  First Patient Found with " + targetRefills + " refills: " +
            firstZero.map(entry -> entry.getKey() + " (" + entry.getValue() + " refills)").orElse("None"));
    }

    // --- Helper Methods for Safe Input ---
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getIntInput(String prompt, int min, int max) {
        int value = -1;
        while (true) {
            try {
                System.out.print(prompt + (min == 0 && max == Integer.MAX_VALUE ? "" : " (" + min + "-" + max + ")") + ": ");
                value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Invalid range.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (e.g., 75.99).");
            }
        }
    }
}