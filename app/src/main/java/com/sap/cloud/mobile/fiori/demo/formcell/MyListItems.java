package com.sap.cloud.mobile.fiori.demo.formcell;


import java.io.Serializable;

class MyListItems implements Serializable {
    private String fuiItemText;
    private Boolean fuiItemSelected;

    String getFuiItemText() {
        return fuiItemText;
    }

    void setFuiItemText(String fuiItemText) {
        this.fuiItemText = fuiItemText;
    }

    Boolean getFuiItemSelected() {
        return fuiItemSelected;
    }

    void setFuiItemSelected(Boolean fuiItemSelected) {
        this.fuiItemSelected = fuiItemSelected;
    }

    boolean isSelected() {
        return (fuiItemSelected);
    }
}
