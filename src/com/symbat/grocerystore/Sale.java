package com.symbat.grocerystore;

public class Sale {

    // -------- FIELDS (ENCAPSULATION) --------
    private int saleId;
    private String customerName;
    private double totalAmount;
    private String date;

    // -------- CONSTRUCTORS --------
    // IMPORTANT: use setters inside constructor to apply validation
    public Sale(int saleId, String customerName, double totalAmount, String date) {
        setSaleId(saleId);
        setCustomerName(customerName);
        setTotalAmount(totalAmount);
        setDate(date);
    }

    public Sale() {
        this.saleId = 0;
        this.customerName = "Unknown";
        this.totalAmount = 0.0;
        this.date = "N/A";
    }

    // -------- GETTERS --------
    public int getSaleId() { return saleId; }
    public String getCustomerName() { return customerName; }
    public double getTotalAmount() { return totalAmount; }
    public String getDate() { return date; }

    // -------- SETTERS (WITH VALIDATION) --------
    public void setSaleId(int saleId) {
        if (saleId > 0) {
            this.saleId = saleId;
        } else {
            System.out.println("Warning: Sale ID must be positive! Setting to 0.");
            this.saleId = 0;
        }
    }

    public void setCustomerName(String customerName) {
        if (customerName != null && !customerName.trim().isEmpty()) {
            this.customerName = customerName.trim();
        } else {
            System.out.println("Warning: Customer name cannot be empty! Keeping previous value.");
        }
    }

    public void setTotalAmount(double totalAmount) {
        if (totalAmount >= 0) {
            this.totalAmount = totalAmount;
        } else {
            System.out.println("Warning: Total amount cannot be negative! Setting to 0.");
            this.totalAmount = 0.0;
        }
    }

    public void setDate(String date) {
        if (date != null && !date.trim().isEmpty()) {
            this.date = date.trim();
        } else {
            System.out.println("Warning: Date cannot be empty! Setting to N/A.");
            this.date = "N/A";
        }
    }

    // -------- BUSINESS METHODS --------
    public void addItem(Product product, int quantity) {
        if (product == null) {
            System.out.println("Warning: Product is null!");
            return;
        }
        if (quantity <= 0) {
            System.out.println("Warning: Quantity must be positive!");
            return;
        }

        boolean sold = product.sell(quantity);
        if (sold) {
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

    // -------- TO STRING --------
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
