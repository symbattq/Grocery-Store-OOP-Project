package com.symbat.grocerystore;

public class Sale {

    // 1. Private fields
    private int saleId;
    private String customerName;
    private double totalAmount;
    private String date;

    // 2. Parameterized constructor
    public Sale(int saleId, String customerName, double totalAmount, String date) {
        this.saleId = saleId;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.date = date;
    }

    // 3. Default constructor
    public Sale() {
        this.saleId = 0;
        this.customerName = "Unknown";
        this.totalAmount = 0.0;
        this.date = "N/A";
    }

    // 4. Getters
    public int getSaleId() {
        return saleId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getDate() {
        return date;
    }

    // 5. Setters
    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // 6. Business logic methods
    public void addItem(Product product, int quantity) {
        if (product != null && quantity > 0 && product.sell(quantity)) {
            totalAmount += product.getPrice() * quantity;
        }
    }
    public double calculateTotal() {
        return totalAmount;
    }

    public double calculateTotalWithDiscount(Customer customer) {
        if (customer == null) return totalAmount;
        return totalAmount * (1 - customer.getDiscountRate());
    }

    // 7. toString method
    @Override
    public String toString() {
        return "Sale{" +
                "saleId=" + saleId +
                ", customerName='" + customerName + '\'' +
                ", totalAmount=" + totalAmount +
                ", date='" + date + '\'' +
                '}';
    }
}
