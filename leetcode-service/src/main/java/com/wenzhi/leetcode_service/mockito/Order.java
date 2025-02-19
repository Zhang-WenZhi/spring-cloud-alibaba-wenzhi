package com.wenzhi.leetcode_service.mockito;

// 订单类
public class Order {
    private double amount;
    private boolean valid;

    public Order(double amount, boolean valid) {
        this.amount = amount;
        this.valid = valid;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isValid() {
        return valid;
    }
}