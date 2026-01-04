package com.symbat.grocerystore;

import java.util.ArrayList;
import java.util.Scanner;

public class GroceryStore {

    // -------- STORAGE --------
    private static ArrayList<Product> products = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // -------- TEST DATA --------
        products.add(new Product(1, "Rice", 800, 20));
        products.add(new FreshProduct(2, "Milk", 500, 10, 5, "Local Farm"));
        products.add(new PackagedProduct(3, "Pasta", 650, 30, 1.0, true));

        boolean running = true;

        while (running) {
            displayMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> addFreshProduct();
                case 3 -> addPackagedProduct();
                case 4 -> viewAllProducts();              // polymorphic list
                case 5 -> demonstratePolymorphism();      // same method, different behavior
                case 6 -> viewFreshProducts();             // instanceof + downcasting
                case 7 -> viewPackagedProducts();          // instanceof + downcasting
                case 0 -> running = false;
                default -> System.out.println("Invalid choice.");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
        System.out.println("Program finished.");
    }

    // -------- MENU --------
    private static void displayMenu() {
        System.out.println("\n==============================");
        System.out.println(" GROCERY STORE SYSTEM");
        System.out.println("==============================");
        System.out.println("1. Add Product (Parent)");
        System.out.println("2. Add Fresh Product");
        System.out.println("3. Add Packaged Product");
        System.out.println("4. View All Products (Polymorphic)");
        System.out.println("5. Demonstrate Polymorphism");
        System.out.println("6. View Fresh Products Only");
        System.out.println("7. View Packaged Products Only");
        System.out.println("0. Exit");
        System.out.println("==============================");
    }

    // -------- ADD METHODS --------
    private static void addProduct() {
        System.out.println("\n--- ADD PRODUCT ---");
        int id = readInt("Product ID: ");
        String name = readString("Name: ");
        double price = readDouble("Price: ");
        int stock = readInt("Stock quantity: ");

        products.add(new Product(id, name, price, stock));
        System.out.println("Product added.");
    }

    private static void addFreshProduct() {
        System.out.println("\n--- ADD FRESH PRODUCT ---");
        int id = readInt("Product ID: ");
        String name = readString("Name: ");
        double price = readDouble("Price: ");
        int stock = readInt("Stock quantity: ");
        int days = readInt("Days to expire: ");
        String farm = readString("Farm name: ");

        products.add(new FreshProduct(id, name, price, stock, days, farm));
        System.out.println("Fresh product added.");
    }

    private static void addPackagedProduct() {
        System.out.println("\n--- ADD PACKAGED PRODUCT ---");
        int id = readInt("Product ID: ");
        String name = readString("Name: ");
        double price = readDouble("Price: ");
        int stock = readInt("Stock quantity: ");
        double weight = readDouble("Package weight: ");
        boolean recyclable = readBoolean("Recyclable (true/false): ");

        products.add(new PackagedProduct(id, name, price, stock, weight, recyclable));
        System.out.println("Packaged product added.");
    }

    // -------- VIEW METHODS --------
    private static void viewAllProducts() {
        System.out.println("\n--- ALL PRODUCTS (POLYMORPHIC LIST) ---");

        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i));
        }
    }

    // -------- POLYMORPHISM DEMO --------
    private static void demonstratePolymorphism() {
        System.out.println("\n--- POLYMORPHISM DEMONSTRATION ---");

        for (Product p : products) {
            p.handle();   // same method, different behavior
        }
    }

    // -------- VIEW BY TYPE (instanceof + downcasting) --------
    private static void viewFreshProducts() {
        System.out.println("\n--- FRESH PRODUCTS ONLY ---");

        int count = 0;
        for (Product p : products) {
            if (p instanceof FreshProduct) {
                FreshProduct fp = (FreshProduct) p; // downcasting
                count++;
                System.out.println(count + ". " + fp);
                System.out.println("   Farm: " + fp.getFarmName());
                System.out.println("   Days to expire: " + fp.getDaysToExpire());
            }
        }

        if (count == 0) {
            System.out.println("No fresh products found.");
        }
    }

    private static void viewPackagedProducts() {
        System.out.println("\n--- PACKAGED PRODUCTS ONLY ---");

        int count = 0;
        for (Product p : products) {
            if (p instanceof PackagedProduct) {
                PackagedProduct pp = (PackagedProduct) p; // downcasting
                count++;
                System.out.println(count + ". " + pp);
                System.out.println("   Packaging: " + pp.packagingInfo());
                System.out.println("   Heavy package: " + pp.isHeavyPackage());
            }
        }

        if (count == 0) {
            System.out.println("No packaged products found.");
        }
    }

    // -------- INPUT HELPERS --------
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            }
            scanner.nextLine();
            System.out.println("Invalid number. Try again.");
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            }
            scanner.nextLine();
            System.out.println("Invalid number. Try again.");
        }
    }

    private static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextBoolean()) {
                boolean value = scanner.nextBoolean();
                scanner.nextLine();
                return value;
            }
            scanner.nextLine();
            System.out.println("Enter true or false.");
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
