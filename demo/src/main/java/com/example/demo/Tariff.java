package com.example.demo;

public class Tariff {
    private String fio;
    private String number;
    private String type;
    private double price;

    public Tariff(String fio, String number, String type, double price) {
        this.fio = fio;
        this.number = number;
        this.type = type;
        this.price = price;
    }

    public String getFio() {
        return fio;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
}
