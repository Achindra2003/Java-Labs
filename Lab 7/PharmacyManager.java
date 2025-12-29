import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class PharmacyManager {
    private Map<String, Medicine> inventory;
    private TreeMap<LocalDate, List<Medicine>> expiryMap;
    private Stack<Transaction> salesHistory;
    private final String FILE_NAME = "pharmacy_data.ser";

    public PharmacyManager() {
        inventory = new HashMap<>();
        expiryMap = new TreeMap<>();
        salesHistory = new Stack<>();
        loadData();
    }

    public void addMedicine(String id, String name, double price, int quantity, String date) {
        Medicine med = new Medicine(id, name, price, quantity, date);
        inventory.put(id, med);
        
        LocalDate exp = LocalDate.parse(date);
        expiryMap.putIfAbsent(exp, new ArrayList<>());
        expiryMap.get(exp).add(med);
        
        saveData();
        System.out.println("Medicine added and indexed by expiry date.");
    }

    public void sellMedicine(String id, int qty) {
        if (!inventory.containsKey(id)) {
            System.out.println("Error: ID not found.");
            return;
        }
        
        Medicine med = inventory.get(id);
        if (med.getQuantity() < qty) {
            System.out.println("Error: Insufficient stock.");
            return;
        }

        med.setQuantity(med.getQuantity() - qty);
        salesHistory.push(new Transaction(id, qty));
        System.out.println("Sale recorded. Added to Undo Stack.");
        saveData();
    }

    public void undoLastSale() {
        if (salesHistory.isEmpty()) {
            System.out.println("No transactions to undo.");
            return;
        }

        Transaction lastTx = salesHistory.pop();
        Medicine med = inventory.get(lastTx.getMedicineId());
        
        if (med != null) {
            med.setQuantity(med.getQuantity() + lastTx.getQuantitySold());
            System.out.println("Undo Successful: Restored " + lastTx.getQuantitySold() + " units of " + med.getName());
            saveData();
        }
    }

    public void showExpiryReport() {
        System.out.println("\n--- Expiry Timeline (TreeMap) ---");
        LocalDate today = LocalDate.now();
        
        Map<LocalDate, List<Medicine>> upcoming = expiryMap.tailMap(today, true);
        
        if (upcoming.isEmpty()) {
            System.out.println("No upcoming expiries.");
        } else {
            upcoming.forEach((date, list) -> {
                System.out.println("Expiring on: " + date);
                list.forEach(m -> System.out.println("  -> " + m.getName() + " (Qty: " + m.getQuantity() + ")"));
            });
        }
    }

    public void generateStockValueReport() {
        System.out.println("\n--- Stock Value Analysis (Streams) ---");
        double totalValue = inventory.values().stream()
            .mapToDouble(m -> m.getPrice() * m.getQuantity())
            .sum();
        
        System.out.printf("Total Inventory Asset Value: $%.2f%n", totalValue);
        
        Medicine mostExpensive = inventory.values().stream()
            .max(Comparator.comparingDouble(Medicine::getPrice))
            .orElse(null);
            
        if (mostExpensive != null) {
            System.out.println("Most Expensive Unit: " + mostExpensive.getName() + " ($" + mostExpensive.getPrice() + ")");
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            inventory = (Map<String, Medicine>) ois.readObject();
            expiryMap = (TreeMap<LocalDate, List<Medicine>>) ois.readObject();
            System.out.println("[System] Data loaded from disk.");
        } catch (FileNotFoundException e) {
            System.out.println("[System] No existing data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[System] Error loading data: " + e.getMessage());
        }
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            oos.writeObject(expiryMap);
        } catch (IOException e) {
            System.out.println("[System] Error saving data: " + e.getMessage());
        }
    }
}