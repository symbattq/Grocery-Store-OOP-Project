package model;

public class Customer {

    private int customerId;
    private String name;
    private String membershipLevel; // Regular / Gold / VIP
    private double totalPurchases;

    public Customer(int customerId, String name, String membershipLevel, double totalPurchases) {
        setCustomerId(customerId);
        setName(name);
        setMembershipLevel(membershipLevel);
        setTotalPurchases(totalPurchases);
    }

    public Customer() {
        this.customerId = 1;
        this.name = "Unknown Customer";
        this.membershipLevel = "Regular";
        this.totalPurchases = 0.0;
    }

    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getMembershipLevel() { return membershipLevel; }
    public double getTotalPurchases() { return totalPurchases; }

    // Week 6: setters throw exceptions
    public void setCustomerId(int customerId) {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive: " + customerId);
        }
        this.customerId = customerId;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        this.name = name.trim();
    }

    public void setMembershipLevel(String membershipLevel) {
        if (membershipLevel == null || membershipLevel.trim().isEmpty()) {
            throw new IllegalArgumentException("Membership level cannot be empty");
        }

        String level = membershipLevel.trim().toLowerCase();
        if (level.equals("vip")) this.membershipLevel = "VIP";
        else if (level.equals("gold")) this.membershipLevel = "Gold";
        else if (level.equals("regular")) this.membershipLevel = "Regular";
        else throw new IllegalArgumentException("Unknown membership level: " + membershipLevel);
    }

    public void setTotalPurchases(double totalPurchases) {
        if (totalPurchases < 0) {
            throw new IllegalArgumentException("Total purchases cannot be negative: " + totalPurchases);
        }
        this.totalPurchases = totalPurchases;
    }

    public void addPurchase(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Purchase amount must be positive: " + amount);
        }
        totalPurchases += amount;
    }

    public boolean isVIP() {
        return membershipLevel.equalsIgnoreCase("VIP") || totalPurchases >= 100000;
    }

    public double getDiscountRate() {
        if (membershipLevel.equalsIgnoreCase("VIP")) return 0.10;
        if (membershipLevel.equalsIgnoreCase("Gold")) return 0.05;
        return 0.0;
    }

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
