package com.sap.cloud.mobile.fiori.demo.formcell;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.formcell.DateTimePicker;
import com.sap.cloud.mobile.fiori.formcell.DateTimePickerFormCell;
import com.sap.cloud.mobile.fiori.formcell.Duration;
import com.sap.cloud.mobile.fiori.formcell.DurationPickerFormCell;
import com.sap.cloud.mobile.fiori.formcell.FormCell;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeAndDurationPickerTestActivity extends AbstractDemoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_date_time_duration_picker);
        DateTimePickerFormCell datetimeCell = findViewById(R.id.datetimeCell);
        datetimeCell.setKey("Date Time");
        datetimeCell.setDateTimePickerMode(DateTimePicker.DateTimePickerMode.DATE_TIME_PICKER);

        datetimeCell.setCellValueChangeListener(new FormCell.CellValueChangeListener<Date>() {
            @Override
            protected void cellChangeHandler(Date value) {
                Toast.makeText(getBaseContext(), value.toString(), Toast.LENGTH_SHORT).show();
            }

            @Nullable
            @Override
            public CharSequence updatedDisplayText(@Nullable Date value) {
                return null;
            }
        });

        datetimeCell.setValue(new Date());

        DateTimePickerFormCell dateCell = findViewById(R.id.dateCell);
        dateCell.setDateTimeFormatter(new SimpleDateFormat("MMM dd, yyyy"));
        dateCell.setValue(new Date());

        DateTimePickerFormCell timeCell = findViewById(R.id.timeCell);
        timeCell.setKey("Time only");
        timeCell.setDateTimePickerMode(DateTimePicker.DateTimePickerMode.TIME_PICKER);
        timeCell.setValue(new Date());

        DurationPickerFormCell durationCell = findViewById(R.id.durationCell);
        durationCell.setKey("Test Duration Picker");
        durationCell.setCellValueChangeListener(new FormCell.CellValueChangeListener<Duration>() {
            @Override
            public void cellChangeHandler(Duration value) {
                Log.i("Returned duration ", String.valueOf(value));
            }
        });
    }

    @NonNull
    public String listenerForTimeCell(Date value) {
        Log.i("Returned Time", " ");
        return "";
    }
}
