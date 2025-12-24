package test;
import pharmacy.Customer;

public class PharmacyTest {
    public static void main(String[] args) {
        Customer c = new Customer("John Doe", "Delhi", "pass123", "DISC50", 120);

        System.out.println("--- Accessing from DIFFERENT package (test) ---");

        c.showPublicProfile();
        System.out.println("Masked Password: " + c.getMaskedPassword());

        // Default data & method: NOT accessible from a different package
        c.showLoyaltyPoints();         
        System.out.println(c.loyaltyPoints); 

        // Private data & method: never accessible from another class
        c.showSensitiveData();      
        System.out.println(c.password); 

        // Calling the new public method that handles private data internally
        c.displayFullProfileForInternalUse();
    }
}