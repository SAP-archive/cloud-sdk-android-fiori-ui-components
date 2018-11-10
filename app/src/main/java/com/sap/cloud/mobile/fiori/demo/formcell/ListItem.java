package com.sap.cloud.mobile.fiori.demo.formcell;


public class ListItem {
    private String name;
    private int id;

    public ListItem(String s) {
        this.name = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
