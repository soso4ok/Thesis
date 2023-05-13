package com.example.thesis.modules;

import java.io.Serializable;

/* loaded from: C:\Users\nxdgb\OneDrive\Рабочий стол\base_source_from_JADX\resources\classes7.dex */
public class DrinkModel implements Serializable {
    private int count;
    private int id;
    private String imageURL;
    private String name;
    private int price;
    private int savedOriginalPrice;
    private String text;

    public DrinkModel(int id, String name, String text, int price, int savedOriginalPrice, String imageURL, int count) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.price = price;
        this.savedOriginalPrice = savedOriginalPrice;
        this.imageURL = imageURL;
        this.count = count;
    }

    public DrinkModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSavedOriginalPrice() {
        return savedOriginalPrice;
    }

    public void setSavedOriginalPrice(int savedOriginalPrice) {
        this.savedOriginalPrice = savedOriginalPrice;
    }
}
