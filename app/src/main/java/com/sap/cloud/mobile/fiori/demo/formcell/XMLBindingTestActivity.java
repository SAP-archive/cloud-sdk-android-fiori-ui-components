package com.sap.cloud.mobile.fiori.demo.formcell;

import android.os.Bundle;
import android.widget.TextView;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.formcell.ChoiceFormCell;
import com.sap.cloud.mobile.fiori.formcell.DurationPickerFormCell;
import com.sap.cloud.mobile.fiori.formcell.FormCell;
import com.sap.cloud.mobile.fiori.formcell.GenericListPickerFormCell;
import com.sap.cloud.mobile.fiori.formcell.SliderFormCell;
import com.sap.cloud.mobile.fiori.formcell.SwitchFormCell;
import com.sap.cloud.mobile.fiori.hierarchy.HierarchyObjectCell;
import com.sap.cloud.mobile.fiori.object.ObjectCell;

import java.util.Collections;

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
                return value + " miles";
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
        genericTextPickerLongIdDemo.setValue(Collections.singletonList(2l));
        genericTextPickerLongIdDemo.setSingleSelectOnly(true);

        GenericListPickerFormCell<TextView, String> genericTextPickerStringIdDemo = findViewById(R.id.genericTextPickerStringId);
        genericTextPickerStringIdDemo.showSelected(true);
        genericTextPickerStringIdDemo.setSelectedItemLabel("Selected Items");
        genericTextPickerStringIdDemo.setAllItemLabel("All Items");
        genericTextPickerStringIdDemo.setPickerActivity(new GenericTextPickerStringIdActivity());

        GenericListPickerFormCell<ObjectCell, Integer> genericObjectCellPickerDemo = findViewById(R.id.genericObjectCellPickerDemo);
        genericObjectCellPickerDemo.setPickerActivity(new GenericObjectCellPickerActivity());
        genericObjectCellPickerDemo.setSingleSelectOnly(false);
        genericObjectCellPickerDemo.setLeftToRight(false);
        genericObjectCellPickerDemo.setLayoutRes(R.layout.activity_generic_listpicker_objectcell, R.id.filterList);
//        genericObjectCellPickerDemo.setLeftToRight(false);

        GenericListPickerFormCell<HierarchyObjectCell, String> genericHierarchyObjectCellPickerDemo = findViewById(R.id.genericHierarchyObjectCellPicker);
        genericHierarchyObjectCellPickerDemo.setPickerActivity(new GenericHierarchyObjectCellPickerActivity());
        genericHierarchyObjectCellPickerDemo.setSingleSelectOnly(false);
//        genericHierarchyObjectCellPickerDemo.setLeftToRight(false);

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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
