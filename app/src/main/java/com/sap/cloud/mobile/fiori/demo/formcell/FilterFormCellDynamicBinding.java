package com.sap.cloud.mobile.fiori.demo.formcell;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.formcell.FilterFormCell;
import com.sap.cloud.mobile.fiori.formcell.FormCell;
import com.sap.cloud.mobile.fiori.formcell.SwitchFormCell;

import java.util.ArrayList;
import java.util.List;

public class FilterFormCellDynamicBinding extends AbstractDemoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_form_cell_dynamic_binding);
        FilterFormCell checkBoxes = findViewById(R.id.checkBoxes);
        FilterFormCell multiLineChips = findViewById(R.id.multiLineChips);
        FilterFormCell singleLineChips = findViewById(R.id.singleLineChips);
        SwitchFormCell outlinedSwitch = findViewById(R.id.outlinedSwitch);
        SwitchFormCell editableSwitch = findViewById(R.id.editableSwitch);

        multiLineChips.setCellValueChangeListener(new FormCell.CellValueChangeListener<List<Integer>>() {
            @Override
            protected void cellChangeHandler(@Nullable List<Integer> value) {
                if(value != null && value.size() > 4) {
                    multiLineChips.setErrorEnabled(true);
                    multiLineChips.setErrorTextAppearance(R.style.error_appearance);
                    multiLineChips.setError("This is an error message");
                }
                else {
                    multiLineChips.setErrorEnabled(false);
                }
            }
        });

        outlinedSwitch.setCellValueChangeListener(new FormCell.CellValueChangeListener<Boolean>() {
            @Override
            protected void cellChangeHandler(@Nullable Boolean value) {
                checkBoxes.setOutlined(value);
                multiLineChips.setOutlined(value);
                singleLineChips.setOutlined(value);
            }
        });

        editableSwitch.setCellValueChangeListener(new FormCell.CellValueChangeListener<Boolean>() {
            @Override
            protected void cellChangeHandler(@Nullable Boolean value) {
                checkBoxes.setEditable(value);
                multiLineChips.setEditable(value);
                singleLineChips.setEditable(value);
            }
        });

        checkBoxes.setHelperTextAppearance(R.style.TextAppearance_Fiori_Formcell_Success_SuccessTooltip);
    }
}
