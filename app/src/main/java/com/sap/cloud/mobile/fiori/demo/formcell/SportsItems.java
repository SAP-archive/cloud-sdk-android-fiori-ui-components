package com.sap.cloud.mobile.fiori.demo.formcell;

import java.util.ArrayList;


class SportsItems {
    String itemName;
    Boolean selected;
    ArrayList<SportsItems> itemList;

    public SportsItems(String s, boolean b) {
        this.itemName = s;
        this.selected = b;
    }

    public ArrayList<SportsItems> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<SportsItems> itemList) {
        this.itemList = itemList;
    }

    public String getitemName() {
        return this.itemName;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean b) {
        this.selected = b;
    }

}
