public class Medicine {
    String name;
    double price;
    int quantity;
    String manufacturer;
    String expiryDate;

    public Medicine() {
        name = "Unknown";
        price = 0.0;
        quantity = 0;
        manufacturer = "Unknown";
        expiryDate = "N/A";
    }

    public Medicine(String name, double price, int quantity, String manufacturer, String expiryDate) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
    }

    public Medicine(String name, double price) {
        this.name = name;
        this.price = price;
        this.quantity = 0;
        this.manufacturer = "Unknown";
        this.expiryDate = "N/A";
    }

    public void display() {
        System.out.println("Name: " + name + " (length: " + name.length() + ")");
        if (manufacturer.equals("Unknown")) {
            System.out.println("Manufacturer not provided");
        } else {
            System.out.println("Manufacturer starts with: " + manufacturer.charAt(0));
        }
        System.out.println("Price: " + price);
        System.out.println("Quantity: " + quantity);
        System.out.println("Manufacturer: " + manufacturer);
        System.out.println("Expiry Date: " + expiryDate);
        System.out.println("-----------------------------");
    }

    public void updateQuantity(int newQty) {
        this.quantity = newQty;
    }

    public StringBuffer generateLabel() {
        StringBuffer label = new StringBuffer();
        label.append("Medicine: ").append(name);
        label.insert(0, "[Label] ");
        label.append(" | Qty: ").append(quantity);
        if (label.length() > 15) {
            label.delete(15, 20 < label.length() ? 20 : label.length());
        }
        label.reverse();
        label.reverse();
        label.replace(0, 7, "Label:");
        return label;
    }
}
