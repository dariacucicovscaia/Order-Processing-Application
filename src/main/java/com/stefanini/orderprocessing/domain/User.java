package com.stefanini.orderprocessing.domain;


public class User {
    private int id;
    private String address;
    private String email;
    private boolean paymentDone;

    public User() {
    }

    public User(String address, String email, boolean paymentDone) {
        this.address = address;
        this.email = email;
        this.paymentDone = paymentDone;
    }

    public User(int id, String address, String email, boolean paymentDone) {
        this.id = id;
        this.address = address;
        this.email = email;
        this.paymentDone = paymentDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPaymentDone() {
        return paymentDone;
    }

    public void setPaymentDone(boolean paymentDone) {
        this.paymentDone = paymentDone;
    }

}
