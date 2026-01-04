package com.symbat.grocerystore;

public class PackagedProduct extends Product {

    // -------- CHILD FIELDS --------
    private double packageWeight;
    private boolean recyclable;

    // -------- CONSTRUCTOR --------
    public PackagedProduct(int productId, String name, double price, int stockQuantity,
                           double packageWeight, boolean recyclable) {
        super(productId, name, price, stockQuantity);
        setPackageWeight(packageWeight);
        setRecyclable(recyclable);
    }

    // -------- GETTERS --------
    public double getPackageWeight() {
        return packageWeight;
    }

    public boolean isRecyclable() {
        return recyclable;
    }

    // -------- SETTERS (WITH VALIDATION) --------
    public void setPackageWeight(double packageWeight) {
        this.packageWeight = packageWeight > 0 ? packageWeight : 0.0;
    }

    public void setRecyclable(boolean recyclable) {
        this.recyclable = recyclable;
    }

    // -------- OVERRIDDEN METHODS --------
    @Override
    public void handle() {
        System.out.println("Packaged product " + name +
                " stored in warehouse. Recyclable: " + recyclable);
    }

    @Override
    public String getType() {
        return "Packaged Product";
    }

    // -------- CHILD METHODS --------
    public boolean isHeavyPackage() {
        return packageWeight > 2.0;
    }

    public String packagingInfo() {
        return recyclable ? "Eco-friendly packaging" : "Regular packaging";
    }

    // -------- TO STRING --------
    @Override
    public String toString() {
        return super.toString() +
                " | Weight: " + packageWeight +
                "kg, Recyclable: " + recyclable;
    }
}
