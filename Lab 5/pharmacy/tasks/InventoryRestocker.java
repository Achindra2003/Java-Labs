package pharmacy.tasks;

import pharmacy.MedicationStock;
import main.PharmacySimulation;

public class InventoryRestocker extends Thread {

    private final MedicationStock stock;
    private final int restockAmount;

    public InventoryRestocker(String name, MedicationStock stock, int restockAmount) {
        super(name); 
        this.stock = stock;
        this.restockAmount = restockAmount;
    }

    @Override
    public void run() {
        try {
            while (PharmacySimulation.isPharmacyOpen) {
                
                int shipmentTime = 4000 + (int) (Math.random() * 3000);
                Thread.sleep(shipmentTime);

                System.out.println("... " + getName() + " shipment of " + restockAmount + " units arrived.");
                
                // Attempt to restock (may wait if shelves are full)
                stock.restock(restockAmount, getName());
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " thread was interrupted.");
        }
        System.out.println("--- " + getName() + " signing off. ---");
    }
}