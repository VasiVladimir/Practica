package com.example.demo;

public class User {

    private String fio;
    private String address;
    private String tariff;
    private double price;
    private double balance;
    private String status;

    public User(String fio, String address, String tariff, double price, double balance, String status) {
        this.fio = fio;
        this.address = address;
        this.tariff = tariff;
        this.price = price;
        this.balance = balance;
        this.status = status;
    }

    public String getFio() {
        return fio;
    }

    public String getAddress() {
        return address;
    }

    public String getTariff() {
        return tariff;
    }

    public double getPrice() {
        return price;
    }

    public double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }
}
