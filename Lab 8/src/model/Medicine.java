package model;

import java.time.LocalDate;

public class Medicine {
    private String id;
    private String name;
    private int quantity;
    private double price;
    private LocalDate expiryDate;
    private String sourceCategory; // NEW: Tracks which file/folder this came from

    public Medicine(String id, String name, int quantity, double price, LocalDate expiryDate, String sourceCategory) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = expiryDate;
        this.sourceCategory = sourceCategory;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    // Self-Learning: Encapsulated logic
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    public void reduceStock(int amount) {
        this.quantity -= amount;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-5s | %-15s | Qty: %-3d | $%-6.2f", 
            sourceCategory, id, name, quantity, price);
    }
}