package menu;

import database.ProductDAO;
import exception.InvalidInputException;
import model.*;

import java.util.List;
import java.util.Scanner;

public class MenuManager implements Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final ProductDAO productDAO = new ProductDAO(); // DB is the only source of truth

    @Override
    public void displayMenu() {
        System.out.println("\n==============================");
        System.out.println(" GROCERY STORE SYSTEM - Week 8");
        System.out.println("==============================");
        System.out.println("1. Add Fresh Product");
        System.out.println("2. Add Packaged Product");
        System.out.println("3. View All Products");
        System.out.println("4. View Fresh Products");
        System.out.println("5. View Packaged Products");
        System.out.println("6. Update Product (SAFE)");
        System.out.println("7. Delete Product (SAFE)");
        System.out.println("8. Search by Name");
        System.out.println("9. Search by Price Range");
        System.out.println("10. High-priced Products (price >= X)");
        System.out.println("11. Polymorphism Demo");
        System.out.println("0. Exit");
        System.out.println("==============================");
    }

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
                    case 4 -> viewFreshProducts();
                    case 5 -> viewPackagedProducts();
                    case 6 -> updateProductSafe();
                    case 7 -> deleteProductSafe();
                    case 8 -> searchByName();
                    case 9 -> searchByPriceRange();
                    case 10 -> highPriceProducts();
                    case 11 -> polymorphismDemo();
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

    // -------------------------
    // CREATE
    // -------------------------
    private void addFreshProduct() {
        String name = readString("Name: ");
        double price = readDouble("Price: ");
        int stock = readInt("Stock quantity: ");
        int days = readInt("Days to expire: ");
        String farm = readString("Farm name: ");

        productDAO.insertFreshProduct(name, price, stock, days, farm);
        System.out.println("✅ Fresh product inserted into DB.");
    }

    private void addPackagedProduct() {
        String name = readString("Name: ");
        double price = readDouble("Price: ");
        int stock = readInt("Stock quantity: ");
        double w = readDouble("Package weight (kg): ");
        boolean recyclable = readBoolean("Recyclable? (true/false): ");

        productDAO.insertPackagedProduct(name, price, stock, w, recyclable);
        System.out.println("✅ Packaged product inserted into DB.");
    }

    // -------------------------
    // READ
    // -------------------------
    private void viewAllProducts() {
        List<Product> products = productDAO.getAllProducts();

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        System.out.println("\n--- ALL PRODUCTS (FROM DB) ---");
        for (Product p : products) {
            System.out.println(p);
            System.out.println("   Storage: " + p.getStorageInfo());
        }
    }

    private void viewFreshProducts() {
        List<Product> products = productDAO.getAllProducts();
        int count = 0;

        for (Product p : products) {
            if (p instanceof FreshProduct fp) {
                count++;
                System.out.println(count + ". " + fp);
                System.out.println("   Farm: " + fp.getFarmName());
                System.out.println("   Days to expire: " + fp.getDaysToExpire());
            }
        }
        if (count == 0) System.out.println("No fresh products found.");
    }

    private void viewPackagedProducts() {
        List<Product> products = productDAO.getAllProducts();
        int count = 0;

        for (Product p : products) {
            if (p instanceof PackagedProduct pp) {
                count++;
                System.out.println(count + ". " + pp);
                System.out.println("   Weight: " + pp.getPackageWeight() + " kg");
                System.out.println("   Recyclable: " + pp.isRecyclable());
                System.out.println("   Packaging info: " + pp.packagingInfo());
                System.out.println("   Heavy package: " + pp.isHeavyPackage());
            }
        }
        if (count == 0) System.out.println("No packaged products found.");
    }

    // -------------------------
    // UPDATE (SAFE)
    // Steps:
    // 1) Ask ID
    // 2) Load from DB
    // 3) Show current values
    // 4) Enter new values or press Enter to keep old
    // 5) Call correct update method based on type
    // -------------------------
    private void updateProductSafe() {
        int id = readInt("Enter product ID to update: ");

        Product existing = productDAO.getProductById(id);
        if (existing == null) {
            System.out.println("❌ No product found with ID: " + id);
            return;
        }

        System.out.println("Current product:");
        System.out.println(existing);

        System.out.print("New name [" + existing.getName() + "]: ");
        String newName = scanner.nextLine().trim();
        if (newName.isEmpty()) newName = existing.getName();

        System.out.print("New price [" + existing.getPrice() + "]: ");
        String priceIn = scanner.nextLine().trim();
        double newPrice = priceIn.isEmpty() ? existing.getPrice() : Double.parseDouble(priceIn);

        System.out.print("New stock [" + existing.getStockQuantity() + "]: ");
        String stockIn = scanner.nextLine().trim();
        int newStock = stockIn.isEmpty() ? existing.getStockQuantity() : Integer.parseInt(stockIn);

        boolean ok;

        if (existing instanceof FreshProduct oldFresh) {
            System.out.print("New days to expire [" + oldFresh.getDaysToExpire() + "]: ");
            String daysIn = scanner.nextLine().trim();
            int newDays = daysIn.isEmpty() ? oldFresh.getDaysToExpire() : Integer.parseInt(daysIn);

            System.out.print("New farm name [" + oldFresh.getFarmName() + "]: ");
            String farmIn = scanner.nextLine().trim();
            if (farmIn.isEmpty()) farmIn = oldFresh.getFarmName();

            FreshProduct updated = new FreshProduct(id, newName, newPrice, newStock, newDays, farmIn);
            ok = productDAO.updateFreshProduct(updated);

        } else if (existing instanceof PackagedProduct oldPack) {
            System.out.print("New package weight [" + oldPack.getPackageWeight() + "]: ");
            String wIn = scanner.nextLine().trim();
            double newWeight = wIn.isEmpty() ? oldPack.getPackageWeight() : Double.parseDouble(wIn);

            System.out.print("New recyclable [" + oldPack.isRecyclable() + "] (true/false): ");
            String recIn = scanner.nextLine().trim();
            boolean newRecyclable = recIn.isEmpty() ? oldPack.isRecyclable() : Boolean.parseBoolean(recIn);

            PackagedProduct updated = new PackagedProduct(id, newName, newPrice, newStock, newWeight, newRecyclable);
            ok = productDAO.updatePackagedProduct(updated);

        } else {
            // Fallback for BASIC rows (should not happen if you changed Sugar to PACKAGED)
            System.out.println("Unknown product type. Cannot update.");
            return;
        }

        System.out.println(ok ? "✅ Updated!" : "❌ Update failed!");
    }

    // -------------------------
    // DELETE (SAFE)
    // Steps:
    // 1) Ask ID
    // 2) Load and show product
    // 3) Ask confirmation yes/no
    // 4) Delete only if yes
    // -------------------------
    private void deleteProductSafe() {
        int id = readInt("Enter product ID to delete: ");

        Product existing = productDAO.getProductById(id);
        if (existing == null) {
            System.out.println("❌ No product found with ID: " + id);
            return;
        }

        System.out.println("Product to delete:");
        System.out.println(existing);

        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine().trim();

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        boolean ok = productDAO.deleteProduct(id);
        System.out.println(ok ? "✅ Deleted!" : "❌ Delete failed!");
    }

    // -------------------------
    // SEARCH
    // -------------------------
    private void searchByName() {
        String name = readString("Enter name to search: ");
        List<Product> results = productDAO.searchByName(name);

        if (results.isEmpty()) System.out.println("No results.");
        else results.forEach(System.out::println);
    }

    private void searchByPriceRange() {
        double min = readDouble("Min price: ");
        double max = readDouble("Max price: ");

        List<Product> results = productDAO.searchByPriceRange(min, max);

        if (results.isEmpty()) System.out.println("No results.");
        else results.forEach(System.out::println);
    }

    private void highPriceProducts() {
        double min = readDouble("Enter minimum price: ");
        List<Product> results = productDAO.searchByMinPrice(min);

        if (results.isEmpty()) System.out.println("No results.");
        else results.forEach(System.out::println);
    }

    // -------------------------
    // Polymorphism demo (from DB)
    // -------------------------
    private void polymorphismDemo() {
        System.out.println("\n--- POLYMORPHISM DEMO (FROM DB) ---");
        List<Product> products = productDAO.getAllProducts();
        for (Product p : products) {
            p.handle(); // runtime polymorphism
        }
    }

    // -------------------------
    // Input helpers
    // -------------------------
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
