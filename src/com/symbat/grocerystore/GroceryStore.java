package com.symbat.grocerystore;

public class GroceryStore {
    public static void main(String[] args) {

        System.out.println("""
        === Welcome to the Grocery Store Management System ===
        This project focuses on managing grocery store operations efficiently.
        Our system helps automate product management, sales processing, and inventory control.

        Main features we plan to implement:
        1. Product catalog and category management
        2. Customer purchase and billing system
        3. Inventory and stock level tracking
        4. Supplier and order management
        5. Sales reports and revenue analysis

        Developed by: Symbat Tursynbay
        Course: Object-Oriented Programming (OOP)
        """);
// Create Product objects
        Product p1 = new Product(1, "Milk", 500, 10);
        Product p2 = new Product(2, "Bread", 250, 5);
        Product p3 = new Product(); // Default constructor

        // Create Customer objects
        Customer c1 = new Customer(1, "Symbat", "Gold", 40000);
        Customer c2 = new Customer(2, "John Smith", "VIP", 120000);

        // Create Sale objects
        Sale s1 = new Sale(1001, c1.getName(), 0.0, "2025-12-21");
        Sale s2 = new Sale(1002, c2.getName(), 0.0, "2025-12-21");

        // Step 3: Display All Objects
        System.out.println("--- PRODUCTS ---");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println();

        System.out.println("--- CUSTOMERS ---");
        System.out.println(c1);
        System.out.println(c2);
        System.out.println();

        System.out.println("--- SALES ---");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println();

        // Step 4: Test Getters
        System.out.println("--- TESTING GETTERS ---");
        System.out.println("Product 1 name: " + p1.getName());
        System.out.println("Product 1 price: " + p1.getPrice() + " KZT");
        System.out.println("Customer 1 membership: " + c1.getMembershipLevel());
        System.out.println("Sale 1 date: " + s1.getDate());
        System.out.println();

        // Step 5: Test Setters
        System.out.println("--- TESTING SETTERS ---");
        System.out.println("Updating p3...");
        p3.setProductId(3);
        p3.setName("Eggs");
        p3.setPrice(900);
        p3.setStockQuantity(20);
        System.out.println("Updated: " + p3);
        System.out.println();

        System.out.println("Changing s2 customer...");
        s2.setCustomerName("Peter Parker");
        System.out.println("Updated: " + s2);
        System.out.println();

        // Step 6: Test Additional Methods
        System.out.println("--- TESTING PRODUCT METHODS ---");
        System.out.println(p1.getName() + " in stock: " + p1.isInStock());
        System.out.println("Selling 2 units of " + p1.getName());
        p1.sell(2);
        System.out.println("After selling: " + p1);
        System.out.println("Restocking 5 units of " + p1.getName());
        p1.restock(5);
        System.out.println("After restocking: " + p1);
        System.out.println();

        System.out.println("--- TESTING CUSTOMER METHODS ---");
        System.out.println(c1.getName() + " VIP: " + c1.isVIP());
        System.out.println("Adding purchase 10000 to " + c1.getName());
        c1.addPurchase(10000);
        System.out.println(c1.getName() + " total purchases: " + c1.getTotalPurchases());
        System.out.println(c1.getName() + " discount rate: " + (c1.getDiscountRate() * 100) + "%");
        System.out.println();

        System.out.println("--- TESTING SALE METHODS ---");
        System.out.println("Adding items to sale " + s1.getSaleId());
        s1.addItem(p2, 1);
        s1.addItem(p3, 5);

        System.out.println("Sale " + s1.getSaleId() +
                " total (no discount): " + s1.calculateTotal() + " KZT");

        System.out.println("Sale " + s1.getSaleId() +
                " total with discount: " + s1.calculateTotalWithDiscount(c1) + " KZT");
        System.out.println();


        // Step 7: Final Summary
        System.out.println("--- FINAL STATE ---");
        System.out.println("Products:");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println();

        System.out.println("Customers:");
        System.out.println(c1);
        System.out.println(c2);
        System.out.println();

        System.out.println("Sales:");
        System.out.println(s1);
        System.out.println(s2);

        System.out.println("\n=== Program Complete ===");
    }
}