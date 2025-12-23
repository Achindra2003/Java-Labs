public class PharmacyStore {
    Medicine[] stock;
    int count;

    public PharmacyStore(int size) {
        stock = new Medicine[size];
        count = 0;
    }

    public void addMedicine(Medicine m) {
        if (count < stock.length) {
            stock[count] = m;
            count++;
        } else {
            System.out.println("Store full, cannot add more medicine.");
        }
    }

    public void showAll() {
        for (int i = 0; i < count; i++) {
            stock[i].display();
            System.out.println("Generated Label: " + stock[i].generateLabel());
            System.out.println("=============================");
        }
    }

    public boolean searchMedicineByName(String searchName) {
        for (int i = 0; i < count; i++) {
            if (stock[i].name.equals(searchName)) {
                System.out.println("Found: " + stock[i].name);
                return true;
            }
        }
        System.out.println("Not found: " + searchName);
        return false;
    }
}
