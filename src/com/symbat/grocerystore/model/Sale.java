package com.symbat.grocerystore.model;

public class Sale {

    private int saleId;
    private String customerName;
    private double totalAmount;
    private String date;

    public Sale(int saleId, String customerName, double totalAmount, String date) {
        setSaleId(saleId);
        setCustomerName(customerName);
        setTotalAmount(totalAmount);
        setDate(date);
    }

    public Sale() {
        this.saleId = 1;
        this.customerName = "Unknown";
        this.totalAmount = 0.0;
        this.date = "N/A";
    }

    public int getSaleId() { return saleId; }
    public String getCustomerName() { return customerName; }
    public double getTotalAmount() { return totalAmount; }
    public String getDate() { return date; }

    // Week 6: setters throw exceptions
    public void setSaleId(int saleId) {
        if (saleId <= 0) {
            throw new IllegalArgumentException("Sale ID must be positive: " + saleId);
        }
        this.saleId = saleId;
    }

    public void setCustomerName(String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        this.customerName = customerName.trim();
    }

    public void setTotalAmount(double totalAmount) {
        if (totalAmount < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative: " + totalAmount);
        }
        this.totalAmount = totalAmount;
    }

    public void setDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException("Date cannot be empty");
        }
        this.date = date.trim();
    }

    public void addItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive: " + quantity);
        }

        product.sell(quantity); // may throw IllegalArgumentException
        totalAmount += product.getPrice() * quantity;
    }

    public double calculateTotal() {
        return totalAmount;
    }

    public double calculateTotalWithDiscount(Customer customer) {
        if (customer == null) return totalAmount;
        return totalAmount * (1 - customer.getDiscountRate());
    }

    @Override
    public String toString() {
        return "Sale{" +
                "saleId=" + saleId +
                ", customerName='" + customerName + '\'' +
                ", totalAmount=" + String.format("%.2f", totalAmount) +
                ", date='" + date + '\'' +
                '}';
    }
}
