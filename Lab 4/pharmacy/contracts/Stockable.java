package pharmacy.contracts;

public interface Stockable {

    void addToStock(int amount);

    boolean isInStock();

    int getStockCount();
}