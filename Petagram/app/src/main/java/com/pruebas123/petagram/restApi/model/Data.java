package com.pruebas123.petagram.restApi.model;

import java.util.ArrayList;

public class Data {
    private ArrayList<Id> data;

    public Data(ArrayList<Id> data) {
        this.data = data;
    }

    public ArrayList<Id> getData() {
        return data;
    }

    public void setData(ArrayList<Id> data) {
        this.data = data;
    }
}
