package pharmacy;

public class Prescription {

    public final String PHARMACY_NAME = "Wellness Pharmacy";
    protected String patientName;
    protected String medicationName;
    private int prescriptionID;

    public Prescription(String patientName, String medicationName, int prescriptionID) {
        this.patientName = patientName;
        this.medicationName = medicationName;
        this.prescriptionID = prescriptionID;
    }

    public int getPrescriptionID() {
        return this.prescriptionID;
    }

    public void displayPrescriptionDetails() {
        System.out.println("Prescription ID: " + this.prescriptionID);
        System.out.println("Patient: " + this.patientName);
        System.out.println("Medication: " + this.medicationName);
    }

    public final void printPharmacyLabel() {
        System.out.println("===============================");
        System.out.println("Property of: " + PHARMACY_NAME);
        System.out.println("Please consult your doctor.");
        System.out.println("===============================");
    }
}

