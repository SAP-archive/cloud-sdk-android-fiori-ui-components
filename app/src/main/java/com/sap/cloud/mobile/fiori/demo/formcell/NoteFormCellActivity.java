package com.sap.cloud.mobile.fiori.demo.formcell;

import static android.view.View.OVER_SCROLL_ALWAYS;
import static android.view.View.SCROLLBARS_INSIDE_INSET;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.formcell.FormCell;
import com.sap.cloud.mobile.fiori.formcell.NoteFormCell;

public class NoteFormCellActivity extends AbstractDemoActivity {

    @Nullable
    private MenuItem mItem;
    private Button button;
    private NoteFormCell note1;
    private NoteFormCell note3;
    private NoteFormCell note4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form_cell);

        note1 = findViewById(R.id.noteWithBorderCell);
        note1.setScrollBarStyle(SCROLLBARS_INSIDE_INSET);
        note1.setValueOverScrollMode(OVER_SCROLL_ALWAYS);
        note1.setCellValueChangeListener(new FormCell.CellValueChangeListener<CharSequence>() {
            @Override
            public void cellChangeHandler(CharSequence value) {
                if (value.length() > 6) {
                    note1.setErrorEnabled(true);
                    note1.setError("This is error");
                } else {
                    note1.setErrorEnabled(false);
                }

            }
        });
        note1.enableVerticalScrollOnNoteFormCell();

        note3 = findViewById(R.id.notNoneEditableCell);
        note4 = findViewById(R.id.notNoneEditableSelectableCell);
        note4.setCellValueChangeListener(new FormCell.CellValueChangeListener<CharSequence>() {
            @Override
            protected void cellChangeHandler(CharSequence value) {
                if (value.length() > 40) {
                    note4.setErrorEnabled(true);
                    note4.setError("This is long message");
                } else {
                    note4.setErrorEnabled(false);
                }
            }
        });
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                note3.getValueView().append("  Some long string to make it wrap.");
                note4.getValueView().append("  Some long string to make it wrap.");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
