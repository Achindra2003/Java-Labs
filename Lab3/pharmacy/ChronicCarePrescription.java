package pharmacy;

public class ChronicCarePrescription extends RefillablePrescription {

    private String diagnosisCode;

    public ChronicCarePrescription(String patientName, String medicationName, int prescriptionID, int refills, String diagnosisCode) {
        super(patientName, medicationName, prescriptionID, refills);
        this.diagnosisCode = diagnosisCode;
    }

    @Override
    public void displayPrescriptionDetails() {
        System.out.println("\n--- Chronic Care Prescription Record ---");
        
        super.displayPrescriptionDetails();
        
        System.out.println("Diagnosis Code (ICD-10): " + this.diagnosisCode);
    }
}