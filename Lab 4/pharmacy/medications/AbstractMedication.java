package pharmacy.medications;

import pharmacy.contracts.Stockable;

public abstract class AbstractMedication implements Stockable {

    private String medicationName;
    private String manufacturer;
    private int stockLevel;

    protected AbstractMedication(String medicationName, String manufacturer, int initialStock) {
        this.medicationName = medicationName;
        this.manufacturer = manufacturer;
        this.stockLevel = initialStock;
    }

    protected void adjustStock(int amount) {
        if (this.stockLevel + amount >= 0) {
            this.stockLevel += amount;
        } else {
            this.stockLevel = 0; // Don't allow negative stock
        }
    }

    @Override
    public void addToStock(int amount) {
        if (amount > 0) {
            System.out.println("Restocking " + this.medicationName + ". Adding " + amount + " units.");
            this.adjustStock(amount); // Uses the protected method
        }
    }

    @Override
    public boolean isInStock() {
        return this.stockLevel > 0;
    }

    @Override
    public int getStockCount() {
        return this.stockLevel;
    }

    public String getMedicationName() {
        return this.medicationName;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }
}