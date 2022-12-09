package com.example.thesis.listView;

public class Drink {

    private String name;
    private String text;
    private String price;
    private int imageID;

    public Drink(String name, String text, int imageID, String price) {
        this.name = name;
        this.text = text;
        this.price = price;
        this.imageID = imageID;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Drink{" +
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


}
