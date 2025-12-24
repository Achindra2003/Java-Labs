package pharmacy;

public class PharmacyStaff {
    public static void main(String[] args) {
        Customer c = new Customer("Jane Smith", "Mumbai", "securePass!", "SUMMER25", 250);

        System.out.println("--- Accessing from SAME package (pharmacy) ---");

        System.out.println("Customer Name: " + c.name);

        System.out.println("Directly accessing loyalty points: " + c.loyaltyPoints);
        c.showLoyaltyPoints(); 

        // Private 
        //System.out.println(c.password); 
    }
}