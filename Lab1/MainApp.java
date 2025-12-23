public class MainApp {
    public static void main(String[] args) {
        PharmacyStore store = new PharmacyStore(3);

        Medicine m1 = new Medicine();
        Medicine m2 = new Medicine("Paracetamol", 25.0, 50, "XYZ Pharma", "12-2026");
        Medicine m3 = new Medicine("Cough Syrup", 60.0);

        m3.updateQuantity(20);

        store.addMedicine(m1);
        store.addMedicine(m2);
        store.addMedicine(m3);

        store.showAll();

        String searchName = "Paracetamol";
        System.out.println("Searching for " + searchName);
        store.searchMedicineByName(searchName);
    }
}
