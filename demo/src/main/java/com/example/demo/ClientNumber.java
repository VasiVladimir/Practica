package com.example.demo;

public class ClientNumber {

    private String fio;
    private String number;
    private String address;
    private String model;

    public ClientNumber(String fio, String number, String address, String model) {
        this.fio = fio;
        this.number = number;
        this.address = address;
        this.model = model;
    }

    public String getFio() {
        return fio;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public String getModel() {
        return model;
    }
}
