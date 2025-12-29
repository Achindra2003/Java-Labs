import java.util.Scanner;

public class PharmacyApp {
    public static void main(String[] args) {
        PharmacyManager manager = new PharmacyManager();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Pharmacy Management System (Persistent) ===");
            System.out.println("1. Add Medicine");
            System.out.println("2. Sell Medicine");
            System.out.println("3. Undo Last Sale (Stack Operation)");
            System.out.println("4. View Expiry Timeline (Sorted Map)");
            System.out.println("5. Stock Analysis");
            System.out.println("6. Exit");
            System.out.print("Select: ");

            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("ID: "); String id = sc.nextLine();
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Price: "); double price = sc.nextDouble();
                    System.out.print("Qty: "); int qty = sc.nextInt();
                    System.out.print("Expiry (YYYY-MM-DD): "); String date = sc.next();
                    manager.addMedicine(id, name, price, qty, date);
                    break;
                case 2:
                    System.out.print("Enter ID: "); String sellId = sc.next();
                    System.out.print("Quantity: "); int sellQty = sc.nextInt();
                    manager.sellMedicine(sellId, sellQty);
                    break;
                case 3:
                    manager.undoLastSale();
                    break;
                case 4:
                    manager.showExpiryReport();
                    break;
                case 5:
                    manager.generateStockValueReport();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        sc.close();
    }
}