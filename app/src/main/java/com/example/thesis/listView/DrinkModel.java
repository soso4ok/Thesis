package com.example.thesis.listView;

import com.google.firebase.database.IgnoreExtraProperties;

public class DrinkModel {

    private String name;
    private String text;
    private int price;
    private String imageURL;

    public DrinkModel(String name, String text, int price, String imageURL) {
        this.name = name;
        this.text = text;
        this.price = price;
        this.imageURL = imageURL;
    }

    public DrinkModel() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
