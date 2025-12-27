package pharmacy.tasks;

import pharmacy.MedicationStock;
import main.PharmacySimulation;

public class SystemAuditor implements Runnable {

    private final MedicationStock stock;
    
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();

    public SystemAuditor(MedicationStock stock) {
        this.stock = stock;
    }

    @Override
    public void run() {
        try {
            while (PharmacySimulation.isPharmacyOpen) {
                
                synchronized (pauseLock) {
                    while (paused) {
                        System.out.println("|| AUDITOR PAUSED ||");
                        pauseLock.wait();
                    }
                }
                
                System.out.println("----- AUDIT: Current stock is " + stock.getStockLevel() + " -----");
                
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            System.out.println("Auditor thread was interrupted.");
        }
        System.out.println("--- Auditor signing off. ---");
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }
}