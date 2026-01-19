package model;

public class PackagedProduct extends Product {

    private double packageWeight;
    private boolean recyclable;

    public PackagedProduct(int productId, String name, double price, int stockQuantity,
                           double packageWeight, boolean recyclable) {
        super(productId, name, price, stockQuantity);
        setPackageWeight(packageWeight);
        setRecyclable(recyclable);
    }

    public double getPackageWeight() {
        return packageWeight;
    }

    public boolean isRecyclable() {
        return recyclable;
    }

    // Setters throw exceptions
    public void setPackageWeight(double packageWeight) {
        if (packageWeight <= 0) {
            throw new IllegalArgumentException("Package weight must be positive: " + packageWeight);
        }
        this.packageWeight = packageWeight;
    }

    public void setRecyclable(boolean recyclable) {
        this.recyclable = recyclable;
    }

    @Override
    public void handle() {
        System.out.println("Packaged product " + name +
                " stored in warehouse. Recyclable: " + recyclable);
    }

    @Override
    public String getType() {
        return "Packaged Product";
    }

    // Implements abstract method from Product
    @Override
    public String getStorageInfo() {
        return "Store in dry place. Weight: " + packageWeight + " kg.";
    }

    public boolean isHeavyPackage() {
        return packageWeight > 2.0;
    }

    public String packagingInfo() {
        return recyclable ? "Eco-friendly packaging" : "Regular packaging";
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Weight: " + packageWeight +
                " kg, Recyclable: " + recyclable;
    }
}
