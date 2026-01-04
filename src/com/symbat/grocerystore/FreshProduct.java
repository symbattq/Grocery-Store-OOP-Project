package com.symbat.grocerystore;

public class FreshProduct extends Product {

    // -------- CHILD FIELDS --------
    private int daysToExpire;
    private String farmName;

    // -------- CONSTRUCTOR --------
    public FreshProduct(int productId, String name, double price, int stockQuantity,
                        int daysToExpire, String farmName) {
        super(productId, name, price, stockQuantity);
        setDaysToExpire(daysToExpire);
        setFarmName(farmName);
    }

    // -------- GETTERS --------
    public int getDaysToExpire() {
        return daysToExpire;
    }

    public String getFarmName() {
        return farmName;
    }

    // -------- SETTERS (WITH VALIDATION) --------
    public void setDaysToExpire(int daysToExpire) {
        this.daysToExpire = Math.max(daysToExpire, 0);
    }

    public void setFarmName(String farmName) {
        this.farmName = (farmName != null && !farmName.trim().isEmpty())
                ? farmName
                : "Unknown Farm";
    }

    // -------- OVERRIDDEN METHODS --------
    @Override
    public void handle() {
        System.out.println("Fresh product " + name +
                " requires careful storage. Days to expire: " + daysToExpire);
    }

    @Override
    public String getType() {
        return "Fresh Product";
    }

    // -------- CHILD METHODS --------
    public boolean isExpired() {
        return daysToExpire == 0;
    }

    public void reduceDay() {
        if (daysToExpire > 0) {
            daysToExpire--;
        }
    }

    // -------- TO STRING --------
    @Override
    public String toString() {
        return super.toString() +
                " | Farm: " + farmName +
                ", DaysToExpire: " + daysToExpire;
    }
}
