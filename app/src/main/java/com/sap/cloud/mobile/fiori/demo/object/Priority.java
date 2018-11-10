package com.sap.cloud.mobile.fiori.demo.object;

/**
 * Task priority
 */

public enum Priority {
    HIGH,
    NORM,
    LOW;
    @Override
    public String toString() {
        String name = super.toString();
        return name.substring(0,1)+name.substring(1).toLowerCase();
    }
}
