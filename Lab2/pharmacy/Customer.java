package pharmacy;

public class Customer {

    private String password;
    private String discountCode;

    int loyaltyPoints;

    public String name;
    public String city;

    public Customer(String name, String city, String password, String discountCode, int loyaltyPoints) {
        this.name = name;
        this.city = city;
        this.password = password;
        this.discountCode = discountCode;
        this.loyaltyPoints = loyaltyPoints;
    }

    public void showPublicProfile() {
        System.out.println("Name: " + name + ", City: " + city);
    }

    void showLoyaltyPoints() {
        System.out.println("Loyalty Points: " + loyaltyPoints);
    }

    private void showSensitiveData() {
        System.out.println("Password: " + password + ", Discount Code: " + discountCode);
    }

    public String getMaskedPassword() {
        return password.replaceAll(".", "*");
    }

    public void displayFullProfileForInternalUse() {
        System.out.println("\n--- Internal Staff View ---");
        showPublicProfile();
        showLoyaltyPoints(); 
        showSensitiveData(); 
        System.out.println("-------------------------");
    }
}