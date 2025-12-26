package pharmacy.medications;

import pharmacy.contracts.Dispensable;

public class OverTheCounterDrug extends AbstractMedication implements Dispensable {

    private String aisleLocation;

    public OverTheCounterDrug(String name, String manufacturer, int initialStock, String aisle) {
        // Calls the 'protected' super-constructor
        super(name, manufacturer, initialStock);
        this.aisleLocation = aisle;
    }

    @Override
    public void dispense(String customerName) {
        if (isInStock()) {
            System.out.println("\nDispensing OTC item: " + getMedicationName());
            System.out.println("  -> Customer: " + customerName);
            System.out.println("  -> Location: " + this.aisleLocation);
            System.out.println("  -> No counseling required.");
            adjustStock(-1); // Uses the 'protected' method from parent
        } else {
            System.out.println("\nDispense FAILED for " + getMedicationName() + ": Out of stock.");
        }
    }

    @Override
    public String getMedicationInfo() {
        return String.format(
            "[OTC Item: %s] [Manuf: %s] [Aisle: %s] [Stock: %d]",
            getMedicationName(), getManufacturer(), this.aisleLocation, getStockCount()
        );
    }
}