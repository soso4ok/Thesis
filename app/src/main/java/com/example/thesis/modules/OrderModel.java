package com.example.thesis.modules;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderModel implements Serializable {

    private ArrayList<DrinkModel> drinkModelArrayList;
    private int totalOrderPrice;

    public OrderModel(ArrayList<DrinkModel> drinkModelArrayList, int totalOrderPrice) {
        this.drinkModelArrayList = drinkModelArrayList;
        this.totalOrderPrice = totalOrderPrice;
    }


    public ArrayList<DrinkModel> getDrinkModelArrayList() {
        return drinkModelArrayList;
    }

    public void setDrinkModelArrayList(ArrayList<DrinkModel> drinkModelArrayList) {
        this.drinkModelArrayList = drinkModelArrayList;
    }

    public int getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(int totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }
}
