package com.symbat.grocerystore.model;

public class FreshProduct extends Product {

    private int daysToExpire;
    private String farmName;

    public FreshProduct(int productId, String name, double price, int stockQuantity,
                        int daysToExpire, String farmName) {
        super(productId, name, price, stockQuantity);
        setDaysToExpire(daysToExpire);
        setFarmName(farmName);
    }

    public int getDaysToExpire() {
        return daysToExpire;
    }

    public String getFarmName() {
        return farmName;
    }

    // Setters throw exceptions
    public void setDaysToExpire(int daysToExpire) {
        if (daysToExpire < 0) {
            throw new IllegalArgumentException("Days to expire cannot be negative: " + daysToExpire);
        }
        this.daysToExpire = daysToExpire;
    }

    public void setFarmName(String farmName) {
        if (farmName == null || farmName.trim().isEmpty()) {
            throw new IllegalArgumentException("Farm name cannot be empty");
        }
        this.farmName = farmName.trim();
    }

    @Override
    public void handle() {
        System.out.println("Fresh product " + name +
                " requires careful storage. Days to expire: " + daysToExpire);
    }

    @Override
    public String getType() {
        return "Fresh Product";
    }

    // Implements abstract method from Product
    @Override
    public String getStorageInfo() {
        return "Keep refrigerated. Expires in " + daysToExpire + " days.";
    }

    public boolean isExpired() {
        return daysToExpire == 0;
    }

    public void reduceDay() {
        if (daysToExpire > 0) {
            daysToExpire--;
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Farm: " + farmName +
                ", DaysToExpire: " + daysToExpire;
    }
}
