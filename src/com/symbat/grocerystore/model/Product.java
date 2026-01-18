package com.symbat.grocerystore.model;

public abstract class Product implements Storable {

    protected int productId;
    protected String name;
    protected double price;
    protected int stockQuantity;

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

    public int getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }

    //setters throw exceptions
    public void setProductId(int productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Product ID must be positive: " + productId);
        }
        this.productId = productId;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.name = name.trim();
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative: " + price);
        }
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock cannot be negative: " + stockQuantity);
        }
        this.stockQuantity = stockQuantity;
    }

    //abstract method
    public abstract String getStorageInfo();

    public void handle() {
        System.out.println(name + " is being handled as a general product.");
    }

    public String getType() {
        return "General Product";
    }

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public boolean sell(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Sell amount must be positive: " + amount);
        }
        if (amount > stockQuantity) {
            throw new IllegalArgumentException("Not enough stock. Available: " + stockQuantity);
        }
        stockQuantity -= amount;
        return true;
    }

    public void restock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Restock amount must be positive: " + amount);
        }
        stockQuantity += amount;
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
