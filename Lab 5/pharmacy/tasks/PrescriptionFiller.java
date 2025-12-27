package pharmacy.tasks;

import pharmacy.MedicationStock;
import main.PharmacySimulation;

public class PrescriptionFiller implements Runnable {

    private final String name;
    private final MedicationStock stock;
    private final int fillAmount;

    public PrescriptionFiller(String name, MedicationStock stock, int fillAmount) {
        this.name = name;
        this.stock = stock;
        this.fillAmount = fillAmount;
    }

    @Override
    public void run() {
        try {
            while (PharmacySimulation.isPharmacyOpen) {
                
                int timeToProcess = 1000 + (int) (Math.random() * 2000);
                Thread.sleep(timeToProcess);
                
                System.out.println("... " + name + " processing new prescription for " + fillAmount + " units.");
                
                stock.fillPrescription(fillAmount, name);
            }
        } catch (InterruptedException e) {
            System.out.println(name + " thread was interrupted.");
        }
        System.out.println("--- " + name + " signing off. ---");
    }
}