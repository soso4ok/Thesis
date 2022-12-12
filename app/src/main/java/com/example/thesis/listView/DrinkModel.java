package com.example.thesis.listView;

public class DrinkModel {

    private int key;
    private String name;
    private String text;
    private String price;
    private int imageID;

    public DrinkModel(String name, String text, int imageID, String price) {
        this.name = name;
        this.text = text;
        this.price = price;
        this.imageID = imageID;
    }

    public DrinkModel() {
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "DrinkModel{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", price='" + price + '\'' +
                ", imageID=" + imageID +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public int getImageID() {
        return imageID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
