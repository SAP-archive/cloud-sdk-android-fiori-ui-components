package com.sap.cloud.mobile.fiori.demo.formcell;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.demo.databinding.ActivityDataBindingTestBinding;
import com.sap.cloud.mobile.fiori.formcell.ChoiceFormCell;
import com.sap.cloud.mobile.fiori.formcell.DateTimePickerFormCell;
import com.sap.cloud.mobile.fiori.formcell.Duration;
import com.sap.cloud.mobile.fiori.formcell.DurationPickerFormCell;
import com.sap.cloud.mobile.fiori.formcell.FilterFormCell;
import com.sap.cloud.mobile.fiori.formcell.FormCell;
import com.sap.cloud.mobile.fiori.formcell.GenericListPickerFormCell;
import com.sap.cloud.mobile.fiori.formcell.NoteFormCell;
import com.sap.cloud.mobile.fiori.formcell.SliderFormCell;
import com.sap.cloud.mobile.fiori.formcell.SwitchFormCell;

import java.util.Date;
import java.util.List;

public class DataBindingTestActivity extends AbstractDemoActivity {

    private FilterDataClass dataClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDataBindingTestBinding testBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_test);
        testBinding.setActivity(this);
        if (savedInstanceState != null && savedInstanceState.get("BindingData") != null) {
            dataClass = (FilterDataClass) savedInstanceState.get("BindingData");
        } else {
            dataClass = new FilterDataClass();
            dataClass.setPreselectedItems(getResources().getIntArray(R.array.selected_values));
            dataClass.setItemList();
            dataClass.setExpListDisplayText("DefaultNone");
            dataClass.setFilterFormCellSelectedValue(getResources().getIntArray(R.array.selected_values));
            dataClass.setGridValueOptions(getResources().getStringArray(R.array.gridSortValueOptions));
            dataClass.setListPickerSelectedValue(getResources().getIntArray(R.array.selected_values));
            dataClass.setListPickerItemList();
            dataClass.setListPickerDisplayText(dataClass.setDescriptionOfSelected(dataClass.getListPickerSelectedValue(), dataClass.getListPickerItemList()));
            dataClass.setListPickerActivityTitle("DataBindingListPickerTitle");
            dataClass.setDateTimeValue(new Date());
            dataClass.setNoteHasBorder(true);
            dataClass.setNoteHint("testDescription");
            dataClass.setNoteIsEditable(true);
            dataClass.setNoteMaxLines(6);
            dataClass.setNoteMinLines(2);
            dataClass.setDurationValue(new Duration(12, 45));
            dataClass.setDurationKeyName("testDurationPicker");
            dataClass.setDurationMinuteInterval(15);
            dataClass.setDurationTextField("00");
            dataClass.setDurationTextFormat("%d::%d");
            dataClass.setDurationIsEditable(true);
            dataClass.setButtonKeyName("TestButton");
            dataClass.setChoiceCellEntries(new String[]{"Orange", "Red", "Yellow", "Black", "Grey", "Blue", "White"});
        }

        SwitchFormCell switchFormCell = findViewById(R.id.testSwitch);
        switchFormCell.setCellValueChangeListener(new FormCell.CellValueChangeListener<Boolean>() {
            @Override
            protected void cellChangeHandler(@NonNull Boolean value) {
                dataClass.setSwitchValue(value);
            }
        });

        SliderFormCell mSliderFormCell = findViewById(R.id.testSlider);
        mSliderFormCell.setCellValueChangeListener(new FormCell.CellValueChangeListener<Integer>() {
            @Override
            public void cellChangeHandler(@NonNull Integer value) {
                dataClass.setSliderValue(value);
                dataClass.setSliderDisplayText(value + " miles ");
                if (value > 20) {
                    mSliderFormCell.setError("You might be getting too far");
                } else {
                    mSliderFormCell.setError("");
                }
            }

            @Override
            public CharSequence updatedDisplayText(@NonNull Integer value) {
                return value.toString();
            }
        });

        FilterFormCell filterFormCell = findViewById(R.id.testGrid);
        filterFormCell.setCellValueChangeListener(new FormCell.CellValueChangeListener<List<Integer>>() {
            @Override
            public void cellChangeHandler(@NonNull List<Integer> value) {
                int[] newValue = new int[value.size()];
                for (int it = 0; it < value.size(); ++it) {
                    newValue[it] = value.get(it);
                }
                dataClass.setFilterFormCellSelectedValue(newValue);
            }
        });

        GenericListPickerFormCell<TextView, Integer> listPickerFormCell = findViewById(R.id.genericTextPickerIntIdDemo);
        MyListFormCellFilterActivity mListPickerFormCell = new MyListFormCellFilterActivity();
        mListPickerFormCell.setItemList(dataClass.getListPickerItemList());
        listPickerFormCell.setPickerActivity(mListPickerFormCell);
        listPickerFormCell.setCellValueChangeListener(new FormCell.CellValueChangeListener<List<Integer>>() {
            @Override
            public void cellChangeHandler(@NonNull List<Integer> value) {

            }

            @Override
            public CharSequence updatedDisplayText(@NonNull List<Integer> value) {
                int[] newValue = new int[value.size()];
                for (int it = 0; it < value.size(); ++it) {
                    dataClass.getListPickerItemList().get(value.get(it)).setFuiItemSelected(true);
                    newValue[it] = value.get(it);
                }
                dataClass.setListPickerSelectedValue(newValue);
                String displayText = dataClass.setDescriptionOfSelected(newValue,
                        dataClass.getListPickerItemList());
                dataClass.setListPickerDisplayText(displayText);
                return displayText;
            }
        });


        DateTimePickerFormCell dateTimeCell = findViewById(R.id.datetimeCell);
        dateTimeCell.setCellValueChangeListener(new FormCell.CellValueChangeListener<Date>() {
            @Override
            public void cellChangeHandler(@NonNull Date value) {
                dataClass.setDateTimeValue(value);
            }

            @Override
            public CharSequence updatedDisplayText(@NonNull Date value) {
                dataClass.setDateTimeValue(value);
                return value.toString();
            }
        });

        NoteFormCell noteFormCell = findViewById(R.id.noteCell);
        noteFormCell.setCellValueChangeListener(new FormCell.CellValueChangeListener<CharSequence>() {
            @Override
            public void cellChangeHandler(@NonNull CharSequence value) {
                dataClass.setNoteFormCellValue(value.toString());
            }
        });

        DurationPickerFormCell durationPickerFormCell = findViewById(R.id.testDuration);
        durationPickerFormCell.setCellValueChangeListener(new FormCell.CellValueChangeListener<Duration>() {
            @Override
            public void cellChangeHandler(@NonNull Duration value) {
                dataClass.setDurationValue(value);
                CharSequence msg = updatedDisplayText(value);
                if (msg != null) {
                    dataClass.setDurationTextField(msg.toString());
                }
            }
        });

        ChoiceFormCell choiceFormCell = findViewById(R.id.choiceCell);
        choiceFormCell.setCellValueChangeListener(new FormCell.CellValueChangeListener<Integer>() {
            @Override
            public void cellChangeHandler(@NonNull Integer value) {
                dataClass.setChoiceCellValue(value);
            }
        });

        testBinding.setFilterData(dataClass);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable("BindingData", dataClass);
    }

}
