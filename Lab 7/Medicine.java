import java.io.Serializable;
import java.time.LocalDate;

public class Medicine implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private double price;
    private int quantity;
    private LocalDate expiryDate;

    public Medicine(String id, String name, double price, int quantity, String date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.expiryDate = LocalDate.parse(date);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public LocalDate getExpiryDate() { return expiryDate; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%-5s | %-15s | $%-6.2f | Qty: %-3d | Exp: %s", 
            id, name, price, quantity, expiryDate);
    }
}