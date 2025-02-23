package model;

import java.sql.Date;

public class Transaction {
    private int id;
    private int customerId;
    private String packageType;
    private double packageWeight;
    private int totalCost;
    private Date createdAt;
    private String receiptName;
    private String receiptAddress;
    private String receiptPhone;

    public Transaction(int id, int customerId, String packageType, double packageWeight, int totalCost, Date createdAt,
            String receiptName, String receiptAddress, String receiptPhone) {
        this.id = id;
        this.customerId = customerId;
        this.packageType = packageType;
        this.packageWeight = packageWeight;
        this.totalCost = totalCost;
        this.createdAt = createdAt;
        this.receiptName = receiptName;
        this.receiptAddress = receiptAddress;
        this.receiptPhone = receiptPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public double getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(double packageWeight) {
        this.packageWeight = packageWeight;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getReceiptName() {
        return receiptName;
    }

    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName;
    }

    public String getReceiptAddress() {
        return receiptAddress;
    }

    public void setReceiptAddress(String receiptAddress) {
        this.receiptAddress = receiptAddress;
    }

    public String getReceiptPhone() {
        return receiptPhone;
    }

    public void setReceiptPhone(String receiptPhone) {
        this.receiptPhone = receiptPhone;
    }

    public static double roundBerat(double selectedWeight) {
        if (selectedWeight < 1) {
            return 1;
        }

        double roundedselectedWeight = Math.ceil(selectedWeight);
        if (selectedWeight - Math.floor(selectedWeight) < 0.5) {
            return Math.floor(selectedWeight);
        } else {
            return roundedselectedWeight;
        }
    }

}
