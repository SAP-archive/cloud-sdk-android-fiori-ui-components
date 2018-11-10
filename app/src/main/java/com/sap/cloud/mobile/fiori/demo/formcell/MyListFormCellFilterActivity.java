package com.sap.cloud.mobile.fiori.demo.formcell;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.sap.cloud.mobile.fiori.formcell.GenericListPickerFormCellActivity;

import java.util.ArrayList;

public class MyListFormCellFilterActivity extends GenericListPickerFormCellActivity<TextView, Integer> {
    ArrayList<MyListItems> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListPickerDataSource(setData());
    }

    @NonNull
    private ArrayList<String> setData() {
        ArrayList<String> itemList = new ArrayList<>();
        for (int it = 0; it < 100; ++it) {
            itemList.add("Item " + it);
        }
        return itemList;
    }

    private void setListPickerDataSource(ArrayList<String> mListItems) {
        itemList = new ArrayList<>();
        for (int i = 0; i < mListItems.size(); ++i) {
            MyListItems item = new MyListItems();
            item.setFuiItemSelected(false);
            item.setFuiItemText(mListItems.get(i));
            itemList.add(item);
        }
    }

    @NonNull
    @Override
    protected TextView onCreateView(int viewType, Context context) {
        return new TextView(context);
    }

    @Override
    protected void onBindView(@NonNull TextView view, Integer position) {
        view.setText(itemList.get(position).getFuiItemText());
    }

    @Override
    protected int getItemViewType(int position) {
        return 0;
    }

    @Override
    protected int getItemCount() {
        return itemList.size();
    }

    @Override
    protected Integer getId(int pos) {
        return pos;
    }


    public void setItemList(ArrayList<MyListItems> itemList) {
        this.itemList = itemList;
    }
}
