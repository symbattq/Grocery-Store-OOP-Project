package com.symbat.grocerystore;

public class Product {

    // -------- PARENT PROTECTED FIELDS (MIN 4) --------
    protected int productId;
    protected String name;
    protected double price;
    protected int stockQuantity;

    // -------- CONSTRUCTORS --------
    public Product(int productId, String name, double price, int stockQuantity) {
        setProductId(productId);
        setName(name);
        setPrice(price);
        setStockQuantity(stockQuantity);
    }

    public Product() {
        this.productId = 0;
        this.name = "Unknown Product";
        this.price = 0.0;
        this.stockQuantity = 0;
    }

    // -------- GETTERS --------
    public int getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }

    // -------- SETTERS (VALIDATION) --------
    public void setProductId(int productId) {
        if (productId > 0) {
            this.productId = productId;
        } else {
            System.out.println("Warning: Product ID must be positive! Setting to 0.");
            this.productId = 0;
        }
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        } else {
            System.out.println("Warning: Product name cannot be empty! Keeping previous value.");
        }
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            System.out.println("Warning: Price cannot be negative! Setting to 0.");
            this.price = 0.0;
        }
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity >= 0) {
            this.stockQuantity = stockQuantity;
        } else {
            System.out.println("Warning: Stock cannot be negative! Setting to 0.");
            this.stockQuantity = 0;
        }
    }

    // -------- METHODS TO BE OVERRIDDEN (PARENT MIN 3 METHODS) --------
    // Action/work method
    public void handle() {
        System.out.println(name + " is being handled as a general product.");
    }

    // Another method to override
    public String getType() {
        return "General Product";
    }

    // Parent method not necessarily overridden
    public boolean isInStock() {
        return stockQuantity > 0;
    }

    // -------- EXTRA PARENT METHODS --------
    public boolean sell(int amount) {
        if (amount <= 0) {
            System.out.println("Warning: Sell amount must be positive!");
            return false;
        }
        if (amount > stockQuantity) {
            System.out.println("Warning: Not enough stock!");
            return false;
        }
        stockQuantity -= amount;
        return true;
    }

    public void restock(int amount) {
        if (amount > 0) stockQuantity += amount;
        else System.out.println("Warning: Restock amount must be positive!");
    }

    public String getFormattedPrice() {
        return String.format("%.2f KZT", price);
    }

    @Override
    public String toString() {
        return "[" + getType() + "] " + name +
                " (ID: " + productId +
                ", Price: " + String.format("%.2f", price) +
                ", Stock: " + stockQuantity + ")";
    }
}
