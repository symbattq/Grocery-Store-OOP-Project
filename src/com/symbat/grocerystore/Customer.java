package com.symbat.grocerystore;

public class Customer {

    // -------- FIELDS (ENCAPSULATION) --------
    private int customerId;
    private String name;
    private String membershipLevel; // Regular / Gold / VIP
    private double totalPurchases;

    // -------- CONSTRUCTORS --------
    // IMPORTANT: use setters inside constructor to apply validation
    public Customer(int customerId, String name, String membershipLevel, double totalPurchases) {
        setCustomerId(customerId);
        setName(name);
        setMembershipLevel(membershipLevel);
        setTotalPurchases(totalPurchases);
    }

    public Customer() {
        this.customerId = 0;
        this.name = "Unknown Customer";
        this.membershipLevel = "Regular";
        this.totalPurchases = 0.0;
    }

    // -------- GETTERS --------
    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getMembershipLevel() { return membershipLevel; }
    public double getTotalPurchases() { return totalPurchases; }

    // -------- SETTERS (WITH VALIDATION) --------
    public void setCustomerId(int customerId) {
        if (customerId > 0) {
            this.customerId = customerId;
        } else {
            System.out.println("Warning: Customer ID must be positive! Setting to 0.");
            this.customerId = 0;
        }
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        } else {
            System.out.println("Warning: Customer name cannot be empty! Keeping previous value.");
        }
    }

    public void setMembershipLevel(String membershipLevel) {
        if (membershipLevel == null) {
            System.out.println("Warning: Membership cannot be null! Setting to Regular.");
            this.membershipLevel = "Regular";
            return;
        }

        String level = membershipLevel.trim().toLowerCase();
        if (level.equals("vip")) this.membershipLevel = "VIP";
        else if (level.equals("gold")) this.membershipLevel = "Gold";
        else if (level.equals("regular")) this.membershipLevel = "Regular";
        else {
            System.out.println("Warning: Unknown membership '" + membershipLevel + "'. Setting to Regular.");
            this.membershipLevel = "Regular";
        }
    }

    public void setTotalPurchases(double totalPurchases) {
        if (totalPurchases >= 0) {
            this.totalPurchases = totalPurchases;
        } else {
            System.out.println("Warning: Total purchases cannot be negative! Setting to 0.");
            this.totalPurchases = 0.0;
        }
    }

    // -------- BUSINESS METHODS --------
    public void addPurchase(double amount) {
        if (amount > 0) {
            totalPurchases += amount;
        } else {
            System.out.println("Warning: Purchase amount must be positive!");
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

    // -------- TO STRING --------
    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", membershipLevel='" + membershipLevel + '\'' +
                ", totalPurchases=" + String.format("%.2f", totalPurchases) +
                '}';
    }
}
