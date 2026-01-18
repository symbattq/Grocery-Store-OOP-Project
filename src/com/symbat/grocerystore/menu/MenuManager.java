package com.symbat.grocerystore.menu;

import com.symbat.grocerystore.exception.InvalidInputException;
import com.symbat.grocerystore.model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuManager implements Menu {

    private final ArrayList<Product> products = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    // Test data
    public MenuManager() {
        products.add(new FreshProduct(1, "Milk", 500, 10, 5, "Local Farm"));
        products.add(new PackagedProduct(2, "Pasta", 650, 30, 1.0, true));
    }

    // Menu interface method
    @Override
    public void displayMenu() {
        System.out.println("\n==============================");
        System.out.println(" GROCERY STORE SYSTEM");
        System.out.println("==============================");
        System.out.println("1. Add Fresh Product");
        System.out.println("2. Add Packaged Product");
        System.out.println("3. View All Products");
        System.out.println("4. Demonstrate Polymorphism");
        System.out.println("5. View Fresh Products");
        System.out.println("6. View Packaged Products");
        System.out.println("0. Exit");
        System.out.println("==============================");
    }

    // Menu interface method
    @Override
    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();

            try {
                int choice = readInt("Enter choice: ");

                switch (choice) {
                    case 1 -> addFreshProduct();
                    case 2 -> addPackagedProduct();
                    case 3 -> viewAllProducts();
                    case 4 -> demonstratePolymorphism();
                    case 5 -> viewFreshProducts();
                    case 6 -> viewPackagedProducts();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid choice");
                }

            } catch (InvalidInputException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
        System.out.println("Program finished.");
    }

    // -------- ADD METHODS --------

    private void addFreshProduct() {
        int id = readInt("Product ID: ");
        String name = readString("Name: ");
        double price = readDouble("Price: ");
        int stock = readInt("Stock quantity: ");
        int days = readInt("Days to expire: ");
        String farm = readString("Farm name: ");

        products.add(new FreshProduct(id, name, price, stock, days, farm));
        System.out.println("Fresh product added.");
    }

    private void addPackagedProduct() {
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

    private void viewAllProducts() {
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.println((i + 1) + ". " + p);
            System.out.println("   Storage: " + p.getStorageInfo()); // interface usage
        }
    }

    private void demonstratePolymorphism() {
        System.out.println("\n--- POLYMORPHISM DEMO ---");
        for (Product p : products) {
            p.handle(); // runtime polymorphism
        }
    }

    private void viewFreshProducts() {
        int count = 0;
        for (Product p : products) {
            if (p instanceof FreshProduct fp) {
                count++;
                System.out.println(count + ". " + fp);
                System.out.println("   Farm: " + fp.getFarmName());
                System.out.println("   Days to expire: " + fp.getDaysToExpire());
                System.out.println("   Storage: " + fp.getStorageInfo());
            }
        }
        if (count == 0) {
            System.out.println("No fresh products found.");
        }
    }

    private void viewPackagedProducts() {
        int count = 0;
        for (Product p : products) {
            if (p instanceof PackagedProduct pp) {
                count++;
                System.out.println(count + ". " + pp);
                System.out.println("   Packaging: " + pp.packagingInfo());
                System.out.println("   Heavy package: " + pp.isHeavyPackage());
                System.out.println("   Storage: " + pp.getStorageInfo());
            }
        }
        if (count == 0) {
            System.out.println("No packaged products found.");
        }
    }

    // -------- INPUT HELPERS --------

    private int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid integer input");
        }
    }

    private double readDouble(String prompt) {
        System.out.print(prompt);
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid number input");
        }
    }

    private boolean readBoolean(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim().toLowerCase();

        if (input.equals("true")) return true;
        if (input.equals("false")) return false;

        throw new InvalidInputException("Enter true or false");
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
