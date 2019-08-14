package com.example.petsu.Model;

public class Services
{
    private String name,description,image,price,location,sid,category,date,time;
    public Services()
    {

    }

    public Services(String name, String description, String image, String price, String location, String sid, String category, String date, String time) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.location = location;
        this.sid = sid;
        this.category = category;
        this.date = date;
        this.time = time;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
