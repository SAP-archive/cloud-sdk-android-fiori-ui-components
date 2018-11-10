package com.sap.cloud.mobile.fiori.demo.misc;

import android.support.annotation.NonNull;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An Item for KeyValueCell demo
 */

public class Item {
    private static Lorem sLorem = LoremIpsum.getInstance();
    private String mValue;

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

    @NonNull
    public static List<Item> createItems(KeyValueSpec spec) {
        Lorem lorem = sLorem;

        List<Item> items = new ArrayList<>();
        Random r = new Random();
        for (int i = 1; i <= spec.getCounts(); i++) {
            Item obj = new Item();
            if (spec.isClickable()){
                obj.setValue(lorem.getPhone());
            }else {
                obj.setValue(cap(lorem.getWords(spec.getMinWords(), spec.getMaxWords())));
            }
            items.add(obj);
        }

        return items;
    }

    private static String cap(String input){
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getValue();
    }
}
