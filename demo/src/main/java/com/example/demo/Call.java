package com.example.demo;

public class Call {

    private String fioOutgoing;
    private String fioIncoming;
    private String outgoingNumber;
    private String incomingNumber;

    public Call(String fioOutgoing, String fioIncoming, String outgoingNumber, String incomingNumber) {
        this.fioOutgoing = fioOutgoing;
        this.fioIncoming = fioIncoming;
        this.outgoingNumber = outgoingNumber;
        this.incomingNumber = incomingNumber;
    }

    public String getFioOutgoing() {
        return fioOutgoing;
    }

    public String getFioIncoming() {
        return fioIncoming;
    }

    public String getOutgoingNumber() {
        return outgoingNumber;
    }

    public String getIncomingNumber() {
        return incomingNumber;
    }
}
