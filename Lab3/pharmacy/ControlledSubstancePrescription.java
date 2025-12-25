package pharmacy;

public class ControlledSubstancePrescription extends Prescription {

    private String deaNumber;

    public ControlledSubstancePrescription(String patientName, String medicationName, int prescriptionID, String deaNumber) {
        // System.out.println("Initializing subclass...");
        super(patientName, medicationName, prescriptionID);
        this.deaNumber = deaNumber;
    }

    @Override
    public void displayPrescriptionDetails() {
        System.out.println("\n--- Controlled Substance Record ---");
        super.displayPrescriptionDetails();
        System.out.println("Prescriber DEA Number: " + this.deaNumber);
    }
}

