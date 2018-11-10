package com.sap.cloud.mobile.fiori.demo.formcell;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.formcell.FilterFormCell;
import com.sap.cloud.mobile.fiori.formcell.FormCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterFormCellDynamicBinding extends AbstractDemoActivity {

    FilterFormCell cell2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_form_cell_dynamic_binding);
        FilterFormCell cell = findViewById(R.id.filterFormCellDynamicBinding);

        cell.setLabelTextAppearanceUnFocused(R.style.TextAppearance_Fiori_Formcell_FormCellKeyUnFocused);
        cell.setKeyTextAppearance(R.style.TextAppearance_Fiori_Formcell_FormCellKey);
        cell.setErrorTextAppearance(R.style.TextAppearance_Fiori_Formcell_Error);
        cell.setKey("Single Line");
        cell.setValueOptions(new String[]{"Not started", "In progress", "On hold"});
        cell.setError("Error message");

        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(2);
        cell.setErrorEnabled(false);
        cell.setValue(selected);

        cell2 = findViewById(R.id.filterFormCellDynamicBinding2);
        FilterFormCell cell3 = findViewById(R.id.filterFormCellDynamicBinding3);
        cell2.setCellValueChangeListener(new FormCell.CellValueChangeListener<List<Integer>>() {
            @Override
            protected void cellChangeHandler(@Nullable List<Integer> value) {

                if (value != null && value.size() > 4) {
                    cell2.setHelperEnabled(false);
                    cell2.setErrorEnabled(true);
                    cell2.setError("Too many values selected.");
                } else if (value != null && value.size() == 0) {
                    cell2.setHelperEnabled(false);
                    cell2.setErrorEnabled(false);
                } else {
                    cell2.setErrorEnabled(false);
                    cell2.setHelperEnabled(true);
                    cell2.setHelperText("Nice choices!");
                }
            }
        });
        cell3.setCellValueChangeListener(new FormCell.CellValueChangeListener<List<Integer>>() {
            @Override
            protected void cellChangeHandler(@Nullable List<Integer> value) {
                cell3.setHelperTextAppearance(R.style.TextAppearance_Fiori_Formcell_Success);
                cell3.setHelperEnabled(true);
                cell3.setHelperText("Success message");
            }
        });

        FilterFormCell cell4 = findViewById(R.id.filterFormCellDynamicBinding4);
        ToggleButton singleLineToggleButton = findViewById(R.id.singleLineToggleButton);
        ToggleButton outlinedToggleButton = findViewById(R.id.outlinedToggleButton);
        ToggleButton useChipToggleButton = findViewById(R.id.useChipToggleButton);
        ToggleButton editableToggleButton = findViewById(R.id.editableToggleButton);

        singleLineToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cell4.setSingleLine(isChecked);
            }
        });

        outlinedToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cell4.setOutlined(isChecked);
            }
        });

        useChipToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cell4.setUseChip(isChecked);
            }
        });

        editableToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cell4.setEditable(isChecked);
            }
        });

    }
}
