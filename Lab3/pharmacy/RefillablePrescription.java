package pharmacy;

public class RefillablePrescription extends Prescription {

    private int numberOfRefills;

    public RefillablePrescription(String patientName, String medicationName, int prescriptionID, int refills) {
        super(patientName, medicationName, prescriptionID);
        this.numberOfRefills = refills;
    }

    @Override
    public void displayPrescriptionDetails() {
        System.out.println("\n--- Refillable Prescription Record ---");
        super.displayPrescriptionDetails();
        System.out.println("Refills Remaining: " + this.numberOfRefills);
    }

    /*
    @Override
    public final void printPharmacyLabel() {
        System.out.println("This is an unauthorized custom label!");
    }
    */
}

