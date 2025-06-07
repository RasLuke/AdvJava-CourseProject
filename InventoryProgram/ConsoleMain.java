package InventoryProgram;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleMain {
	// This list will hold the products for now. in the future this will be replaced with a proper database
    static ArrayList<Product> inventory = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        //Main Menu
        while (running) {
            System.out.println("\n=== SmartStock Inventory Management ===");
            System.out.println("1. View Products");
            System.out.println("2. Add Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> viewProducts(); //Display Inventory
                case 2 -> addProduct(); //Add item
                case 3 -> deleteProduct(); //Remove Item
                case 4 -> running = false; //Exit
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    //Display Inventory
    private static void viewProducts() {
        if (inventory.isEmpty()) {
            System.out.println("No products in inventory.");
            return;
        }

        for (int i = 0; i < inventory.size(); i++) {
            Product p = inventory.get(i);
            System.out.printf("%d. %s | Qty: %d | Price: $%.2f\n", i + 1, p.getName(), p.getQuantity(), p.getPrice());
        }
    }
    //add new product by input
    private static void addProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter quantity: ");
        int qty = scanner.nextInt();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        Product newProduct = new Product(name, qty, price);
        inventory.add(newProduct);
        System.out.println("Product added.");
    }
    // delete product by selecting its index
    private static void deleteProduct() {
        viewProducts();
        System.out.print("Enter product number to delete: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline

        if (index >= 0 && index < inventory.size()) {
            inventory.remove(index);
            System.out.println("Product deleted.");
        } else {
            System.out.println("Invalid product number.");
        }
    }
}
