import java.io.Serializable;

public class Transaction implements Serializable {
    private String medicineId;
    private int quantitySold;

    public Transaction(String medicineId, int quantitySold) {
        this.medicineId = medicineId;
        this.quantitySold = quantitySold;
    }

    public String getMedicineId() { return medicineId; }
    public int getQuantitySold() { return quantitySold; }
}