package com.symbat.grocerystore;

public class Customer {

    // 1. Private fields
    private int customerId;
    private String name;
    private String membershipLevel;
    private double totalPurchases;

    // 2. Parameterized constructor
    public Customer(int customerId, String name, String membershipLevel, double totalPurchases) {
        this.customerId = customerId;
        this.name = name;
        this.membershipLevel = membershipLevel;
        this.totalPurchases = totalPurchases;
    }

    // 3. Default constructor
    public Customer() {
        this.customerId = 0;
        this.name = "Unknown Customer";
        this.membershipLevel = "Regular";
        this.totalPurchases = 0.0;
    }

    // 4. Getters
    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getMembershipLevel() {
        return membershipLevel;
    }

    public double getTotalPurchases() {
        return totalPurchases;
    }

    // 5. Setters
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMembershipLevel(String membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    public void setTotalPurchases(double totalPurchases) {
        this.totalPurchases = totalPurchases;
    }

    // 6. Business logic methods
    public void addPurchase(double amount) {
        if (amount > 0) {
            totalPurchases += amount;
        }
    }

    public boolean isVIP() {
        return membershipLevel.equalsIgnoreCase("VIP") || totalPurchases >= 100000;
    }

    public double getDiscountRate() {
        if (membershipLevel.equalsIgnoreCase("VIP")) return 0.10;
        if (membershipLevel.equalsIgnoreCase("Gold")) return 0.05;
        return 0.0;
    }

    // 7. toString method
    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", membershipLevel='" + membershipLevel + '\'' +
                ", totalPurchases=" + totalPurchases +
                '}';
    }
}
