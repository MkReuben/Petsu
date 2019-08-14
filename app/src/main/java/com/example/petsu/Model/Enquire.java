package com.example.petsu.Model;

public class Enquire {
    private String sid,name,price,discount;

    public Enquire() {
    }

    public Enquire(String sid, String name, String price, String discount) {
        this.sid = sid;
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
