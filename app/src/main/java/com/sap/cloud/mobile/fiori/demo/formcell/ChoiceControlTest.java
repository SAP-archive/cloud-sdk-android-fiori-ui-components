package com.sap.cloud.mobile.fiori.demo.formcell;

import android.os.Bundle;
import android.widget.Toast;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.formcell.ChoiceFormCell;
import com.sap.cloud.mobile.fiori.formcell.FormCell;

public class ChoiceControlTest extends AbstractDemoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_control_test);
        final ChoiceFormCell cell1 = findViewById(R.id.test1);
        cell1.setKey("ApportionsChoice set to true");
        cell1.setValue(2);
        cell1.setValueOptions(new String[]{"First", "Second", "Third"});
        cell1.setCellValueChangeListener(new FormCell.CellValueChangeListener<Integer>() {
            @Override
            public void cellChangeHandler(Integer value) {
                if ((int) value == 0) {
                    cell1.setErrorEnabled(true);
                    cell1.setError("This is invalid selection");
                } else {
                    cell1.setErrorEnabled(false);
                    CharSequence text = "New tab selected is at index " + value;
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        final ChoiceFormCell cell2 = findViewById(R.id.test2);
        cell2.setValueOptions(new String[]{"Tab 1", "Tab 2", "Tab 3", "Tab 4", "Tab 5", "Tab 6", "Tab 7"});
        cell2.setCellValueChangeListener(new FormCell.CellValueChangeListener<Integer>() {
            @Override
            public void cellChangeHandler(Integer value) {
                if ((int) value == 3) {
                    cell2.setErrorEnabled(true);
                    cell2.setError("This view is using the default tab colors");
                } else {
                    cell2.setErrorEnabled(false);
                }
            }
        });

        final ChoiceFormCell cell3 = findViewById(R.id.test3);
        cell3.setValueOptions(new String[]{"Tab1", "Tab2", "Tab3"});
        cell3.setValue(2);
        cell3.setCellValueChangeListener(new FormCell.CellValueChangeListener<Integer>() {
            @Override
            public void cellChangeHandler(Integer value) {
                if ((int) value == 1 || (int) value == 0) {
                    cell3.setErrorEnabled(true);
                    cell3.setError("Is editable is not working correctly");
                } else {
                    cell1.setErrorEnabled(false);
                }
            }
        });
    }
}
