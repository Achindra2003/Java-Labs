package pharmacy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MedicationStock {

    private final String medicationName;
    private int stockLevel;
    private final int maxStock;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition stockAvailable = lock.newCondition();
    private final Condition spaceAvailable = lock.newCondition();

    public MedicationStock(String name, int initialStock, int maxStock) {
        this.medicationName = name;
        this.stockLevel = initialStock;
        this.maxStock = maxStock;
        System.out.println("Inventory created for " + name + ": " + initialStock + " units (Max: " + maxStock + ")");
    }

    public void fillPrescription(int amount, String fillerName) throws InterruptedException {
        // Must explicitly lock
        lock.lock();
        try {
            while (this.stockLevel < amount) {
                System.out.println("!!! " + fillerName + ": WAITING. Not enough " + medicationName + " (Need: " + amount + ", Have: " + stockLevel + ")");
                stockAvailable.await();
                System.out.println("... " + fillerName + " woken up, re-checking stock...");
            }
            
            this.stockLevel -= amount;
            System.out.println("--> " + fillerName + ": FILLED " + amount + " " + medicationName + ". New stock: " + this.stockLevel);
            
            spaceAvailable.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void restock(int amount, String restockerName) throws InterruptedException {
        lock.lock();
        try {
            while (this.stockLevel + amount > this.maxStock) {
                System.out.println("!!! " + restockerName + ": WAITING. Shelves are full. (Have: " + stockLevel + ", Adding: " + amount + ", Max: " + maxStock + ")");
                spaceAvailable.await();
                System.out.println("... " + restockerName + " woken up, re-checking space...");
            }

            this.stockLevel += amount;
            System.out.println("==> " + restockerName + ": RESTOCKED " + amount + " " + medicationName + ". New stock: " + this.stockLevel);
            
            stockAvailable.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public int getStockLevel() {
        lock.lock();
        try {
            return this.stockLevel;
        } finally {
            lock.unlock();
        }
    }
    
    public String getMedicationName() {
        return this.medicationName;
    }
}