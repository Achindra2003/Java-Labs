package pharmacy.medications;

import pharmacy.contracts.Dispensable;

public class PrescriptionDrug extends AbstractMedication implements Dispensable {

    private String prescriberDeaNumber;

    public PrescriptionDrug(String name, String manufacturer, int initialStock, String deaNumber) {
        super(name, manufacturer, initialStock);
        this.prescriberDeaNumber = deaNumber;
    }

    @Override
    public void dispense(String patientName) {
        if (isInStock()) {
            System.out.println("\nDispensing PRESCRIPTION drug: " + getMedicationName());
            System.out.println("  -> Patient: " + patientName);
            System.out.println("  -> Verifying DEA#: " + this.prescriberDeaNumber);
            System.out.println("  -> **Patient counseling required.**");
            adjustStock(-1); // Uses the 'protected' method from parent
        } else {
            System.out.println("\nDispense FAILED for " + getMedicationName() + ": Out of stock.");
        }
    }

    @Override
    public String getMedicationInfo() {
        return String.format(
            "[Rx Drug: %s] [Manuf: %s] [DEA: %s] [Stock: %d]",
            getMedicationName(), getManufacturer(), this.prescriberDeaNumber, getStockCount()
        );
    }
}