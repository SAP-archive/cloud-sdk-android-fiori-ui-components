package com.sap.cloud.mobile.fiori.demo.formcell;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.formcell.ChoiceFormCell;
import com.sap.cloud.mobile.fiori.formcell.DurationPickerFormCell;
import com.sap.cloud.mobile.fiori.formcell.FormCell;
import com.sap.cloud.mobile.fiori.formcell.GenericListPickerFormCell;
import com.sap.cloud.mobile.fiori.formcell.SliderFormCell;
import com.sap.cloud.mobile.fiori.formcell.SwitchFormCell;
import com.sap.cloud.mobile.fiori.object.ObjectCell;

public class XMLBindingTestActivity extends AbstractDemoActivity {

    private SliderFormCell mSliderFormCell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_xml_binding_test);
        DurationPickerFormCell durationPickerFormCell = findViewById(R.id.testDuration);
        FilterDataClass dataClass = new FilterDataClass();

        int[] selectedValue = getResources().getIntArray(R.array.selected_values);

        dataClass.setPreselectedItems(selectedValue);
        dataClass.setStringItemList();

        ChoiceFormCell choiceFormCell = findViewById(R.id.choiceCell);
        ChoiceFormCell choiceFormCell2 = findViewById(R.id.choiceCell2);

        choiceFormCell.setCellValueChangeListener(new FormCell.CellValueChangeListener<Integer>() {
            @Override
            public void cellChangeHandler(Integer value) {
                choiceFormCell2.setValue(value);
            }
        });

        choiceFormCell2.setErrorEnabled(true);
        choiceFormCell2.setCellValueChangeListener(new FormCell.CellValueChangeListener<Integer>() {
            @Override
            public void cellChangeHandler(Integer value) {
                String message = choiceFormCell2.getValueOptions()[value] + " selected";
                choiceFormCell2.setError(message);
            }
        });

        ChoiceFormCell choiceFormCell3 = findViewById(R.id.choiceCell3);
        ToggleButton singleLineToggleButton = findViewById(R.id.singleLineToggleButton);
        ToggleButton outlinedToggleButton = findViewById(R.id.outlinedToggleButton);

        singleLineToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                choiceFormCell3.setSingleLine(isChecked);
            }
        });

        outlinedToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                choiceFormCell3.setOutlined(isChecked);
            }
        });

        mSliderFormCell = findViewById(R.id.testSlider1);

        mSliderFormCell.setCellValueChangeListener(new FormCell.CellValueChangeListener<Integer>() {

            @Override
            protected void cellChangeHandler(Integer value) {
                if (value > 80) {
                    mSliderFormCell.setErrorEnabled(true);
                    mSliderFormCell.setError("You might be getting too far;");
                } else {
                    mSliderFormCell.setErrorEnabled(false);
                }
            }

            @Override
            public CharSequence updatedDisplayText(Integer value) {
                return mSliderFormCell.getDisplayValue() + " " + value + " miles";
            }
        });

        SliderFormCell mSliderFormCell1 = findViewById(R.id.testSlider);
        mSliderFormCell1.setCellValueChangeListener(new FormCell.CellValueChangeListener<Integer>() {
            @Override
            protected void cellChangeHandler(Integer value) {
                if (value > 80) {
                    mSliderFormCell1.setErrorEnabled(true);
                    mSliderFormCell1.setError("Too loud music!");
                } else {
                    mSliderFormCell1.setErrorEnabled(false);
                }
            }
        });

        GenericListPickerFormCell<TextView, Long> genericTextPickerLongIdDemo = findViewById(R.id.genericTextPickerLongIdDemo);
        GenericTextPickerLongIdActivity myFuiListFilter = new GenericTextPickerLongIdActivity();
        genericTextPickerLongIdDemo.setPickerActivity(myFuiListFilter);
        genericTextPickerLongIdDemo.setSingleSelectOnly(true);

        GenericListPickerFormCell<TextView, String> genericTextPickerStringIdDemo = findViewById(R.id.genericTextPickerStringId);
        genericTextPickerStringIdDemo.showSelected(true);
        genericTextPickerStringIdDemo.setSelectedItemLabel("Selected Items");
        genericTextPickerStringIdDemo.setAllItemLabel("All Items");
        genericTextPickerStringIdDemo.setPickerActivity(new GenericTextPickerStringIdActivity());

        GenericListPickerFormCell<ObjectCell, Integer> genericObjectCellPickerDemo = findViewById(R.id.genericObjectCellPickerDemo);
        genericObjectCellPickerDemo.setPickerActivity(new GenericObjectCellPickerActivity());
        genericObjectCellPickerDemo.setSingleSelectOnly(false);
        genericObjectCellPickerDemo.setLeftToRight(true);

        SwitchFormCell cell = findViewById(R.id.testSwitch);
        cell.setCellValueChangeListener(new FormCell.CellValueChangeListener<Boolean>() {
            @Override
            public void cellChangeHandler(Boolean value) {
                if (value) {
                    cell.setHelperText("This is normal state");
                } else {
                    cell.setError("This is error state");
                }
            }
        });
        durationPickerFormCell.setError("This is validation message");

        ToggleButton editableToggleButton = findViewById(R.id.editableToggleButton);
        editableToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                choiceFormCell3.setEditable(isChecked);
                genericTextPickerLongIdDemo.setEditable(isChecked);
                durationPickerFormCell.setEditable(isChecked);
                mSliderFormCell.setEditable(isChecked);
                ((SwitchFormCell) findViewById(R.id.testSwitch)).setEditable(isChecked);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
